package executor

import (
	"context"
	"errors"
	"fmt"
	"io/ioutil"
	"os"
	"path"
	"path/filepath"
	"strings"
	"time"

	"github.com/damingerdai/hoteler/migration/internal/files"
	"github.com/damingerdai/hoteler/migration/internal/schema"
	"github.com/damingerdai/hoteler/migration/internal/times"
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
			"id" integer,
			"version" varchar(100),
			"description" varchar(100),
			"script" varchar(50),
			"checksum" integer,
			"execution_time" integer,
			"success" boolean, 
			"installed_by" varchar(50),
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
	sql := `SELECT "id", "version", "description", "script",  "checksum", "execution_time", "success", "installed_by", "installed_on" FROM schema_migrations`

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
		var installedBy string
		var installedOn time.Time

		rows.Scan(&id, &version, &description, &script, &checksum, &executionTime, &success, &installedBy, &installedOn)

		item := schema.Item{
			Id:            id,
			Version:       version,
			Description:   description,
			Script:        script,
			Checksum:      checksum,
			ExecutionTime: executionTime,
			Success:       success,
			InstalledBy:   installedBy,
			InstalledOn:   installedOn,
		}

		items = append(items, item)
	}

	return &items, nil
}
