module github.com/damingerdai/hoteler/migration

go 1.17

require (
	github.com/golang-migrate/migrate/v4 v4.14.1
	github.com/joho/godotenv v1.3.0
)

require (
	github.com/hashicorp/errwrap v1.0.0 // indirect
	github.com/hashicorp/go-multierror v1.1.0 // indirect
	github.com/inconshreveable/mousetrap v1.0.0 // indirect
	github.com/lib/pq v1.8.0 // indirect
	github.com/spf13/cobra v1.3.0 // indirect
	github.com/spf13/pflag v1.0.5 // indirect
)


replace (
	github.com/damingerdai/hoteler/migration/cmd => ./cmd
	github.com/damingerdai/hoteler/migration/internal => ./internal
	github.com/damingerdai/hoteler/migration/internal/files => ./internal/files
	github.com/damingerdai/hoteler/migration/internal/times => ./internal/times
	github.com/damingerdai/hoteler/migration/pkg/cmd => ./pkg/cmd
)