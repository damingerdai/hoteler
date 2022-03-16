package database

import (
	"fmt"
	"net/url"
	"os"
)

type DB interface {
	GetDBUrl() string
}

type EnvDb struct {
}

func (db *EnvDb) GetDBUrl() string {
	dbUrl := fmt.Sprintf(
		"postgres://%s:%s@%s:%s/%s",
		os.Getenv("POSTGRES_USER"),
		url.QueryEscape(os.Getenv("POSTGRES_PASSWORD")),
		os.Getenv("POSTGRES_HOST"),
		os.Getenv("POSTGRES_PORT"),
		os.Getenv("POSTGRES_DB"),
	)
	sslmode := os.Getenv("POSTGRES_SSLMODE")
	if sslmode != "" {
		dbUrl += "?sslmode=" + sslmode
	}

	return dbUrl
}
