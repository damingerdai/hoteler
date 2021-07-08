package cmd

import (
	"os"
	"path"
	"time"

	"github.com/damingerdai/hoteler/migration/internal/files"
	"github.com/damingerdai/hoteler/migration/internal/times"
)

func CreateCmd(filename string) error {
	dir, err := os.Getwd()
	if err != nil {
		return err
	}
	mirgationPath := path.Join(dir, "migrations")
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
