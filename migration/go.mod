module github.com/damingerdai/hoteler/migration

go 1.21

require (
	github.com/golang-migrate/migrate/v4 v4.16.2
	github.com/jackc/pgx/v5 v5.5.0
	github.com/joho/godotenv v1.5.1
	github.com/spf13/cobra v1.8.0
)

require (
	github.com/hashicorp/errwrap v1.1.0 // indirect
	github.com/hashicorp/go-multierror v1.1.1 // indirect
	github.com/inconshreveable/mousetrap v1.1.0 // indirect
	github.com/jackc/pgpassfile v1.0.0 // indirect
	github.com/jackc/pgservicefile v0.0.0-20221227161230-091c0ba34f0a // indirect
	github.com/jackc/puddle/v2 v2.2.1 // indirect
	github.com/lib/pq v1.10.2 // indirect
	github.com/spf13/pflag v1.0.5 // indirect
	go.uber.org/atomic v1.10.0 // indirect
	golang.org/x/crypto v0.9.0 // indirect
	golang.org/x/sync v0.2.0 // indirect
	golang.org/x/text v0.9.0 // indirect
)

replace (
	github.com/damingerdai/hoteler/migration/cmd => ./cmd
	github.com/damingerdai/hoteler/migration/internal => ./internal
	github.com/damingerdai/hoteler/migration/internal/database => ./internal/database
	github.com/damingerdai/hoteler/migration/internal/files => ./internal/files
	github.com/damingerdai/hoteler/migration/internal/schema => ./internal/schema
	github.com/damingerdai/hoteler/migration/internal/times => ./internal/times
	github.com/damingerdai/hoteler/migration/internal/url => ./internal/url
	github.com/damingerdai/hoteler/migration/internal/util => ./internal/util
	github.com/damingerdai/hoteler/migration/pkg/cmd => ./pkg/cmd
	github.com/damingerdai/hoteler/migration/pkg/executor => ./pkg/executor
)
