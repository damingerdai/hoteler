package cmd

import (
	"fmt"

	"github.com/spf13/cobra"
)

var seedCmd = &cobra.Command{
	Use:   "seed",
	Short: "hoteler的seed命令",
	Long:  "hoteler的seed命令",
	Run: func(cmd *cobra.Command, args []string) {
		fmt.Println("准备seed")
	},
}

func init() {
	rootCmd.AddCommand(seedCmd)
}
