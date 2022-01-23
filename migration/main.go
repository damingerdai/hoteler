package main

import (
	"github.com/damingerdai/hoteler/migration/cmd"
	_ "github.com/joho/godotenv/autoload"
)

func main() {
	cmd.Execute()
}
