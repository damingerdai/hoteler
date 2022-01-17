package cmd

import (
	"fmt"
	"os"

	"github.com/spf13/cobra"
)

var rootCmd = &cobra.Command{
	Use:   "hoteler",
	Short: "hoteler的数据库migrate和seed命令行脚本",
	Long:  "hoteler的数据库migrate和seed命令行脚本",
	Run: func(cmd *cobra.Command, args []string) {
		fmt.Println("这里是hoteler的数据库migrate和seed命令行脚本")
	},
}

func Execute() {
	if err := rootCmd.Execute(); err != nil {
		fmt.Fprintln(os.Stderr, err)
		os.Exit(1)
	}
}
