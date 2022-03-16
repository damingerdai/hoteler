package cmd

import (
	"github.com/damingerdai/hoteler/migration/internal/database"
	"github.com/golang-migrate/migrate/v4"
)

func FetchMigrate() (*migrate.Migrate, error) {
	db := database.EnvDb{}
	dbUrl := db.GetDBUrl()

	return migrate.New(
		"file://./db/migrations",
		dbUrl,
	)
}
