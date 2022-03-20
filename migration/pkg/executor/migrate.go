package executor

import (
	"context"

	"github.com/jackc/pgx/v4/pgxpool"
)

type IMigrateExecutor interface {
	Migrate(version uint) error
	Up() error
	Down() error
	Create() error
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

func (m *MigrateExecutor) Create() error {
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
