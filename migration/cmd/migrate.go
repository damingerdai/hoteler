package cmd

import (
	"fmt"
	"os"
	"strconv"

	idatabase "github.com/damingerdai/hoteler/migration/internal/database"
	"github.com/damingerdai/hoteler/migration/pkg/cmd"
	"github.com/damingerdai/hoteler/migration/pkg/executor"
	"github.com/spf13/cobra"
)

var migrateUpCmd = &cobra.Command{
	Use:   "up",
	Short: "hoteler的migrate up命令",
	Long:  "hoteler的migrate up命令",
	Run: func(command *cobra.Command, args []string) {
		m, err := cmd.FetchMigrateExecutor()
		if err != nil {
			fmt.Println(err)
			return
		}
		defer m.Close()
		err = m.Up()
		fmt.Println(err)
		if err != nil && err.Error() != "no change" {
			fmt.Println(err)
			return
		}

		fmt.Println("migrate up work")
	},
}

var migrateDownCmd = &cobra.Command{
	Use:   "down",
	Short: "hoteler的migrate down命令",
	Long:  "hoteler的migrate down命令",
	Run: func(command *cobra.Command, args []string) {
		m, err := cmd.FetchMigrateExecutor()
		if err != nil {
			fmt.Println(err)
			return
		}
		defer m.Close()
		err = m.Down()
		if err != nil && err.Error() != "no change" {
			fmt.Println(err)
			return
		}

		fmt.Println("migrate down work")
	},
}

var migrateForceCmd = &cobra.Command{
	Use:   "force",
	Short: "hoteler的migrate force命令",
	Long:  "hoteler的migrate force命令",
	Run: func(command *cobra.Command, args []string) {
		versionStr := args[0]
		version, err := strconv.Atoi(versionStr)
		if err != nil {
			fmt.Printf("fail to run migrate script: %s\n", err.Error())
		}
		m, err := cmd.FetchMigrateExecutor()
		if err != nil {
			fmt.Println(err)
			return
		}
		defer m.Close()
		err = m.Force(version)
		if err != nil && err.Error() != "no change" {
			fmt.Println(err)
			return
		}

		fmt.Println("migrate force work")
	},
}

var migrateCreateCmd = &cobra.Command{
	Use:   "create",
	Short: "hoteler的migrate create命令",
	Long:  "hoteler的migrate create命令",
	Args:  cobra.RangeArgs(1, 1),
	Run: func(command *cobra.Command, args []string) {
		fileName := args[0]
		err := cmd.CreateCmd(fileName)
		if err != nil {
			fmt.Println(err)
			return
		}

		fmt.Println("migrate create work")
	},
}

var migrateCmd = &cobra.Command{
	Use:   "migrate",
	Short: "hoteler的migrate命令",
	Long:  "hoteler的migrate命令",
	Args:  cobra.RangeArgs(1, 1),
	Run: func(command *cobra.Command, args []string) {

	},
}

var migrateV2Cmd = &cobra.Command{
	Use:   "migrate2",
	Short: "hoteler的migrate命令2",
	Long:  "hoteler的migrate命令2",
	Args:  cobra.RangeArgs(0, 1),
	Run: func(command *cobra.Command, args []string) {
		filePath := "file://./db/migrations"
		db := idatabase.EnvDb{}
		dbUrl := db.GetDBUrl()
		m, err := executor.New(filePath, dbUrl)
		if err != nil {
			fmt.Println(err)
		}
		defer m.Close()
		versionStr := args[0]
		version, err := strconv.ParseInt(versionStr, 10, 64)
		if err != nil {
			fmt.Fprintln(os.Stderr, err.Error())
			return
		}
		m.Migrate(version)

	},
}

var migrateV2CreateCmd = &cobra.Command{
	Use:   "create",
	Short: "hoteler的migrate v2 create命令",
	Long:  "hoteler的migrate v2 create命令",
	Args:  cobra.RangeArgs(1, 1),
	Run: func(command *cobra.Command, args []string) {
		fileName := args[0]
		filePath := "file://./db/migrations"
		db := idatabase.EnvDb{}
		dbUrl := db.GetDBUrl()
		m, err := executor.New(filePath, dbUrl)
		if err != nil {
			fmt.Println(err)
		}
		m.Create(fileName)
		defer m.Close()
		fmt.Println(m)
	},
}

var migrateV2MigrateUpCmd = &cobra.Command{
	Use:   "up",
	Short: "hoteler的migrate v2 migrate命令",
	Long:  "hoteler的migrate v2 migrate命令",
	Args:  cobra.RangeArgs(0, 1),
	Run: func(command *cobra.Command, args []string) {
		filePath := "file://./db/migrations"
		db := idatabase.EnvDb{}
		dbUrl := db.GetDBUrl()
		m, err := executor.New(filePath, dbUrl)
		if err != nil {
			fmt.Println(err)
		}
		err = m.Up()
		if err != nil {
			fmt.Println(err)
		}
		m.Close()
	},
}

func init() {
	migrateCmd.AddCommand(migrateUpCmd)
	migrateCmd.AddCommand(migrateDownCmd)
	migrateCmd.AddCommand(migrateForceCmd)
	migrateCmd.AddCommand(migrateCreateCmd)
	rootCmd.AddCommand(migrateCmd)

	migrateV2Cmd.AddCommand(migrateV2CreateCmd)
	migrateV2Cmd.AddCommand(migrateV2MigrateUpCmd)
	rootCmd.AddCommand(migrateV2Cmd)
}
