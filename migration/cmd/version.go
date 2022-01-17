package cmd

import (
	"fmt"

	"github.com/spf13/cobra"
)

var versionCmd = &cobra.Command{
	Use:   "version",
	Short: "打印hoteler的数据库命令的版本号",
	Long:  "打印hoteler的数据库命令的版本号",
	Run: func(cmd *cobra.Command, args []string) {
		fmt.Println("hoteler的版本是935e512b5d671d5102aeeefe65677c2d01ed4037")
	},
}

func init() {
	rootCmd.AddCommand(versionCmd)
}
