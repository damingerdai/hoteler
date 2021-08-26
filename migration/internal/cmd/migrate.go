package cmd

import (
	"fmt"
	"net/url"
	"os"

	"github.com/golang-migrate/migrate/v4"
)

func FetchMigrate() (*migrate.Migrate, error) {
	dbUrl := fmt.Sprintf(
		"postgres://%s:%s@%s:%s/%s",
		os.Getenv("POSTGRES_USER"),
		url.QueryEscape(os.Getenv("POSTGRES_PASSWORD")),
		os.Getenv("POSTGRES_HOST"),
		os.Getenv("POSTGRES_PORT"),
		os.Getenv("POSTGRES_DB"),
	)
	sslmode := os.Getenv("POSTGRES_SSLMODE")
	if sslmode == "" {
		dbUrl += "?sslmode=" + sslmode
	}
	return migrate.New(
		"file://./db/migrations",
		dbUrl,
	)
}
