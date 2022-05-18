package executor

import (
	"context"
	"errors"
	"fmt"
	"io/fs"
	"io/ioutil"
	"os"
	"path"
	"path/filepath"
	"strconv"
	"strings"
	"time"

	"github.com/damingerdai/hoteler/migration/internal/files"
	"github.com/damingerdai/hoteler/migration/internal/schema"
	"github.com/damingerdai/hoteler/migration/internal/times"
	"github.com/damingerdai/hoteler/migration/internal/util"
	"github.com/jackc/pgx/v4/pgxpool"
)

type IMigrateExecutor interface {
	Migrate(version int64) error
	Up() error
	Down() error
	Create(fileName string) error
	Close() error
}

type MigrateExecutor struct {
	source   string
	database string

	pool *pgxpool.Pool
}

func New(source, database string) (IMigrateExecutor, error) {
	m := MigrateExecutor{source: source, database: database}
	return &m, nil
}

func (m *MigrateExecutor) Migrate(version int64) error {
	err := m.initSchemaHistory()
	if err != nil {
		return err
	}
	sourcePath := strings.Split(m.source, "file://")[1]
	mirgationPath, err := filepath.Abs(sourcePath)
	if err != nil {
		return err
	}
	files, err := ioutil.ReadDir(mirgationPath)
	if err != nil {
		return err
	}
	var upFile, downFile []byte
	for _, file := range files {
		fileName := file.Name()
		if strings.HasPrefix(fileName, fmt.Sprint(version)) {
			filePath := path.Join(mirgationPath, fileName)
			if strings.HasSuffix(fileName, "down.sql") {
				downFile, _ = ioutil.ReadFile(filePath)
			} else if strings.HasSuffix(fileName, "up.sql") {
				upFile, _ = ioutil.ReadFile(filePath)
			}
		}
		if len(downFile) > 0 && len(upFile) > 0 {
			break
		}
		continue
	}
	upFileContent := string(upFile)
	// downileContent := string(downFile)
	err = m.fetchDBengine()
	if err != nil {
		return err
	}
	m.pool.Exec(context.Background(), upFileContent)
	return nil
}

func (m *MigrateExecutor) Up() error {
	err := m.initSchemaHistory()
	if err != nil {
		return err
	}
	sourcePath := strings.Split(m.source, "file://")[1]
	mirgationPath, err := filepath.Abs(sourcePath)
	if err != nil {
		return err
	}
	newestVersion := int64(0)
	item, err := m.fetchNewestSchemaHistory()
	if err != nil {
		return err
	}
	if item != nil && item.Version != "" {
		newestVersion, err = strconv.ParseInt(item.Version, 10, 64)
		if err != nil {
			return err
		}
		fmt.Println(item)
	}

	if err != nil {
		return err
	}
	files, err := ioutil.ReadDir(mirgationPath)
	upFiles := make([]fs.FileInfo, 0, len(files))
	for _, file := range files {
		fileName := file.Name()
		if !strings.HasSuffix(fileName, "up.sql") {
			continue
		}
		fileVersionStr := strings.Split(fileName, "_")[0]
		fileVersion, err := strconv.ParseInt(fileVersionStr, 10, 64)
		if err != nil {
			return err
		}
		if fileVersion >= newestVersion {
			upFiles = append(upFiles, file)
		}
	}
	for _, file := range upFiles {
		fileName := file.Name()
		filePath := path.Join(mirgationPath, fileName)
		fileContent, _ := ioutil.ReadFile(filePath)
		err = m.fetchDBengine()
		if err != nil {
			return err
		}
		instant := time.Now().UnixNano()
		_, err := m.pool.Exec(context.Background(), string(fileContent))
		duration := time.Now().UnixNano() - instant
		fileVersionStr := strings.Split(fileName, "_")[0]
		descriptionStr := strings.Join(strings.SplitAfterN(fileName, "_", 2), "_")
		fmt.Println(duration)
		fmt.Println(fileVersionStr)
		fmt.Println(descriptionStr)
		fmt.Println(strings.Trim(string(fileContent), " "))
		_, err2 := m.pool.Exec(
			context.Background(),
			"INSERT INTO  schema_migrations (version, description, script, checksum, execution_time, success, installed_on) VALUES ($1, $2, $3, $4, $5, $6, CURRENT_TIMESTAMP) RETURNING id;",
			fileVersionStr,
			descriptionStr,
			fileName,
			util.GetMD5Hash(string(fileContent)),
			duration,
			util.If(err == nil, true, false),
		)
		if err2 != nil {
			fmt.Println(string(fileContent))
			fmt.Println(err2)
			return err2
		}
		if err != nil {
			return err
		}
	}

	return nil
}

func (m *MigrateExecutor) Down() error {
	return nil
}

func (m *MigrateExecutor) Create(fileName string) error {
	sourcePath := strings.Split(m.source, "file://")[1]
	mirgationPath, err := filepath.Abs(sourcePath)
	if err != nil {
		return err
	}
	tv := times.TimeVersion(time.Now())
	upSqlFile := tv + "_" + files.Kebabcase(fileName) + ".up.sql"
	downSqlFile := tv + "_" + files.Kebabcase(fileName) + ".down.sql"
	upFile, err := os.Create(path.Join(mirgationPath, upSqlFile))
	if err != nil {
		return err
	}
	defer upFile.Close()
	downFile, err := os.Create(path.Join(mirgationPath, downSqlFile))
	if err != nil {
		return err
	}
	defer downFile.Close()
	return nil
}

func (m *MigrateExecutor) Close() error {
	if m.pool != nil {
		m.pool.Close()
	}
	return nil
}

func (m *MigrateExecutor) fetchDBengine() error {
	pool, err := pgxpool.Connect(context.Background(), m.database)
	if err != nil {
		return err
	}
	m.pool = pool
	return nil
}

func (m *MigrateExecutor) checkPool() error {
	var err error
	if m.pool == nil {
		err = m.fetchDBengine()
		if err != nil {
			return err
		}
	}
	if m.pool == nil {
		return errors.New("init pool error")
	}

	return nil
}

func (m *MigrateExecutor) initSchemaHistory() error {
	err := m.checkPool()
	if err != nil {
		return err
	}
	sql := `
		CREATE TABLE IF NOT EXISTS "schema_migrations" (
			"id" uuid NOT NULL DEFAULT gen_random_uuid(),
			"version" varchar(100),
			"description" varchar(100),
			"script" varchar(200),
			"checksum" varchar(32),
			"execution_time" integer,
			"success" boolean,
			"installed_on" timestamp DEFAULT NOW(),
			PRIMARY KEY ("id")
		);
	`
	_, err = m.pool.Exec(context.Background(), sql)
	if err != nil {
		return errors.New("fail to create schema_migrations " + err.Error())
	}

	return nil
}

func (m *MigrateExecutor) fetchSchemaHistory() (*schema.Items, error) {
	err := m.initSchemaHistory()
	if err != nil {
		return nil, err
	}
	sql := `SELECT "id", "version", "description", "script",  "checksum", "execution_time", "success", "installed_on" FROM schema_migrations`

	rows, err := m.pool.Query(context.Background(), sql)
	if err != nil {
		return nil, err
	}
	defer rows.Close()
	items := make([]schema.Item, 0)
	for rows.Next() {
		var id int
		var version string
		var description string
		var script string
		var checksum int64
		var executionTime int64
		var success bool
		var installedOn time.Time

		rows.Scan(&id, &version, &description, &script, &checksum, &executionTime, &success, &installedOn)

		item := schema.Item{
			Id:            id,
			Version:       version,
			Description:   description,
			Script:        script,
			Checksum:      checksum,
			ExecutionTime: executionTime,
			Success:       success,
			InstalledOn:   installedOn,
		}

		items = append(items, item)

		fmt.Println(item)
	}

	return &items, nil
}

func (m *MigrateExecutor) fetchNewestSchemaHistory() (*schema.Item, error) {
	sql := `SELECT "id", "version", "description", "script",  "checksum", "execution_time", "success", "installed_on" FROM schema_migrations ORDER BY installed_on DESC LIMIT 1`
	row := m.pool.QueryRow(context.Background(), sql)
	var id int
	var version string
	var description string
	var script string
	var checksum int64
	var executionTime int64
	var success bool
	var installedOn time.Time

	row.Scan(&id, &version, &description, &script, &checksum, &executionTime, &success, &installedOn)

	item := schema.Item{
		Id:            id,
		Version:       version,
		Description:   description,
		Script:        script,
		Checksum:      checksum,
		ExecutionTime: executionTime,
		Success:       success,
		InstalledOn:   installedOn,
	}

	return &item, nil
}
