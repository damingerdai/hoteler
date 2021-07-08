package main

import (
	"flag"
	"fmt"
	"os"

	"github.com/damingerdai/hoteler/migration/internal/cmd"
	_ "github.com/golang-migrate/migrate/v4/database/postgres"
	_ "github.com/golang-migrate/migrate/v4/source/file"
)

func main() {
	flag.Parse()
	fmt.Fprintln(os.Stdout, "migrate db scripts")
	// m, err := migrate.New(
	// 	"file://./db/migrations",
	// 	fmt.Sprintf(
	// 		"postgres://%s:%s@%s:%s/%s?sslmode=disable",
	// 		"postgres",
	// 		url.QueryEscape("123456"),
	// 		"localhost",
	// 		"5432",
	// 		"migration",
	// 	),
	// )
	// if err != nil {
	// 	fmt.Println(err)
	// 	os.Exit(0)
	// }
	// defer m.Close()
	switch flag.Arg(0) {
	// case "up":
	// 	err := m.Up()
	// 	if err != nil && err.Error() != "no change" {
	// 		fmt.Fprintf(os.Stdout, "fail to run migrate script: %s", err.Error())
	// 	}
	// 	os.Exit(0)
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
