package main

import (
	"flag"
	"fmt"
	"os"
	"strconv"

	"github.com/damingerdai/hoteler/migration/internal/cmd"
	_ "github.com/golang-migrate/migrate/v4/database/postgres"
	_ "github.com/golang-migrate/migrate/v4/source/file"
	_ "github.com/joho/godotenv/autoload"
)

func main() {
	flag.Parse()
	fmt.Fprintln(os.Stdout, "migrate db scripts")
	switch flag.Arg(0) {
	case "up":
		m, err := cmd.FetchMigrate()
		if err != nil {
			fmt.Fprintf(os.Stdout, "fail to run migrate script: %s", err.Error())
		}
		defer m.Close()
		err = m.Up()
		if err != nil && err.Error() != "no change" {
			fmt.Fprintf(os.Stdout, "fail to run migrate script: %s", err.Error())
		}
		os.Exit(0)
	case "down":
		m, err := cmd.FetchMigrate()
		if err != nil {
			fmt.Fprintf(os.Stdout, "fail to run migrate script: %s", err.Error())
		}
		defer m.Close()
		err = m.Down()
		if err != nil && err.Error() != "no change" {
			fmt.Fprintf(os.Stderr, "fail to run migrate script: %v", err.Error())
			os.Exit(0)
		}
	case "force":
		versionStr := flag.Arg(1)
		version, err := strconv.Atoi(versionStr)
		if err != nil {
			fmt.Fprintf(os.Stdout, "fail to run migrate script: %s", err.Error())
		}
		m, err := cmd.FetchMigrate()
		if err != nil {
			fmt.Fprintf(os.Stdout, "fail to run migrate script: %s", err.Error())
		}
		defer m.Close()
		err = m.Force(version)
		if err != nil && err.Error() != "no change" {
			fmt.Fprintf(os.Stderr, "fail to run migrate script: %v", err.Error())
			os.Exit(0)
		}
	case "create":
		fileName := flag.Arg(1)
		if fileName == "" {
			fmt.Fprintf(os.Stdout, "fail to run migrate script: please provide the file name")
			os.Exit(0)
		}
		err := cmd.CreateCmd(fileName)
		if err != nil {
			fmt.Fprintf(os.Stdout, "fail to run migrate script: %s", err.Error())
			os.Exit(0)
		}
	}

}
