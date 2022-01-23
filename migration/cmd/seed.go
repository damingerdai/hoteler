package cmd

import (
	"fmt"

	"github.com/damingerdai/hoteler/migration/internal/cmd"
	"github.com/spf13/cobra"
)

var seedCmd = &cobra.Command{
	Use:   "seed",
	Short: "hoteler的seed命令",
	Long:  "hoteler的seed命令",
	Run: func(command *cobra.Command, args []string) {
		seed, err := cmd.FetchSeed()
		if err != nil {
			fmt.Println(err)
			return
		}
		err = seed.Up()
		if err != nil {
			fmt.Println(err)
			return
		}
	},
}

func init() {
	rootCmd.AddCommand(seedCmd)
}
