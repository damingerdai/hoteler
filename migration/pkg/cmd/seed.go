package cmd

import (
	"github.com/damingerdai/hoteler/migration/internal/database"
	"github.com/golang-migrate/migrate/v4"
	_ "github.com/golang-migrate/migrate/v4/database/postgres"
	_ "github.com/golang-migrate/migrate/v4/source/file"
)

func FetchSeed() (*migrate.Migrate, error) {
	db := database.EnvDb{}
	dbUrl := db.GetDBUrl()
	return migrate.New(
		"file://./db/seeds",
		dbUrl,
	)

}
