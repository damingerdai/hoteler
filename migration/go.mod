module github.com/damingerdai/hoteler/migration

go 1.24.0

require (
	github.com/golang-migrate/migrate/v4 v4.18.2
	github.com/jackc/pgx/v5 v5.7.2
	github.com/joho/godotenv v1.5.1
	github.com/spf13/cobra v1.8.1
)

require (
	github.com/hashicorp/errwrap v1.1.0 // indirect
	github.com/hashicorp/go-multierror v1.1.1 // indirect
	github.com/inconshreveable/mousetrap v1.1.0 // indirect
	github.com/jackc/pgpassfile v1.0.0 // indirect
	github.com/jackc/pgservicefile v0.0.0-20240606120523-5a60cdf6a761 // indirect
	github.com/jackc/puddle/v2 v2.2.2 // indirect
	github.com/lib/pq v1.10.9 // indirect
	github.com/spf13/pflag v1.0.5 // indirect
	go.uber.org/atomic v1.10.0 // indirect
	golang.org/x/crypto v0.31.0 // indirect
	golang.org/x/sync v0.10.0 // indirect
	golang.org/x/text v0.21.0 // indirect
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
