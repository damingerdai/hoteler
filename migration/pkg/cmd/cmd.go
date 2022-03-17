package cmd

import (
	"os"
	"path"
	"time"

	"github.com/damingerdai/hoteler/migration/internal/database"
	"github.com/damingerdai/hoteler/migration/internal/files"
	"github.com/damingerdai/hoteler/migration/internal/times"
	"github.com/golang-migrate/migrate/v4"
	_ "github.com/golang-migrate/migrate/v4/database/postgres"
	_ "github.com/golang-migrate/migrate/v4/source/file"
)

func CreateCmd(filename string) error {
	dir, err := os.Getwd()
	if err != nil {
		return err
	}
	mirgationPath := path.Join(dir, "db", "migrations")
	tv := times.TimeVersion(time.Now())
	upSqlFile := tv + "_" + files.Kebabcase(filename) + ".up.sql"
	downSqlFile := tv + "_" + files.Kebabcase(filename) + ".down.sql"
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

func FetchMigrateExecutor() (*migrate.Migrate, error) {
	return createDatasourceExecutor("file://./db/migrations")
}

func FetchSeedExecutor() (*migrate.Migrate, error) {
	return createDatasourceExecutor("file://./db/seeds")
}

func createDatasourceExecutor(filePath string) (*migrate.Migrate, error) {
	db := database.EnvDb{}
	dbUrl := db.GetDBUrl()

	return migrate.New(
		filePath,
		dbUrl,
	)
}

func doCreateDatasourceExecutor(filePath, dbUrl string) (*migrate.Migrate, error) {

	return migrate.New(
		filePath,
		dbUrl,
	)
}
