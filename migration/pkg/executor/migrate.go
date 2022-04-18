package executor

import (
	"context"
	"os"
	"path"
	"path/filepath"
	"strings"
	"time"

	"github.com/damingerdai/hoteler/migration/internal/files"
	"github.com/damingerdai/hoteler/migration/internal/times"
	"github.com/jackc/pgx/v4/pgxpool"
)

type IMigrateExecutor interface {
	Migrate(version uint) error
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

func (m *MigrateExecutor) Migrate(version uint) error {
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
