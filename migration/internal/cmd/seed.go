package cmd

import (
	"fmt"
	"net/url"
	"os"

	"github.com/golang-migrate/migrate/v4"
	_ "github.com/golang-migrate/migrate/v4/database/postgres"
	_ "github.com/golang-migrate/migrate/v4/source/file"
)

func FetchSeed() (*migrate.Migrate, error) {
	dbUrl := fmt.Sprintf(
		"postgres://%s:%s@%s:%s/%s?x-migrations-table=%s",
		os.Getenv("POSTGRES_USER"),
		url.QueryEscape(os.Getenv("POSTGRES_PASSWORD")),
		os.Getenv("POSTGRES_HOST"),
		os.Getenv("POSTGRES_PORT"),
		os.Getenv("POSTGRES_DB"),
		"schema_seeds",
	)

	sslmode := os.Getenv("POSTGRES_SSLMODE")
	if sslmode != "" {
		dbUrl += "&sslmode=" + sslmode
	}
	return migrate.New(
		"file://./db/seeds",
		dbUrl,
	)

}
