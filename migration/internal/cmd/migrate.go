package cmd

import (
	"fmt"
	"net/url"
	"os"

	"github.com/golang-migrate/migrate/v4"
)

func FetchMigrate() (*migrate.Migrate, error) {
	dbUrl := fmt.Sprintf(
		"postgres://%s:%s@%s:%s/%s?sslmode=disable",
		os.Getenv("POSTGRES_USER"),
		url.QueryEscape(os.Getenv("POSTGRES_PASSWORD")),
		os.Getenv("POSTGRES_HOST"),
		os.Getenv("POSTGRES_PORT"),
		os.Getenv("POSTGRES_DB"),
	)
	return migrate.New(
		"file://./db/migrations",
		dbUrl,
	)
}
