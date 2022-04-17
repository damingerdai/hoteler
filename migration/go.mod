module github.com/damingerdai/hoteler/migration

go 1.18

require (
	github.com/golang-migrate/migrate/v4 v4.14.1
	github.com/jackc/pgx/v4 v4.15.0
	github.com/joho/godotenv v1.3.0
	github.com/spf13/cobra v1.3.0
)

require (
	github.com/hashicorp/errwrap v1.0.0 // indirect
	github.com/hashicorp/go-multierror v1.1.0 // indirect
	github.com/inconshreveable/mousetrap v1.0.0 // indirect
	github.com/jackc/chunkreader/v2 v2.0.1 // indirect
	github.com/jackc/pgconn v1.11.0 // indirect
	github.com/jackc/pgio v1.0.0 // indirect
	github.com/jackc/pgpassfile v1.0.0 // indirect
	github.com/jackc/pgproto3/v2 v2.2.0 // indirect
	github.com/jackc/pgservicefile v0.0.0-20200714003250-2b9c44734f2b // indirect
	github.com/jackc/pgtype v1.10.0 // indirect
	github.com/jackc/puddle v1.2.1 // indirect
	github.com/lib/pq v1.10.2 // indirect
	github.com/spf13/pflag v1.0.5 // indirect
	golang.org/x/crypto v0.0.0-20210817164053-32db794688a5 // indirect
	golang.org/x/text v0.3.7 // indirect
)

replace (
	github.com/damingerdai/hoteler/migration/cmd => ./cmd
	github.com/damingerdai/hoteler/migration/internal => ./internal
	github.com/damingerdai/hoteler/migration/internal/database => ./internal/database
	github.com/damingerdai/hoteler/migration/internal/files => ./internal/files
	github.com/damingerdai/hoteler/migration/internal/times => ./internal/times
	github.com/damingerdai/hoteler/migration/pkg/cmd => ./pkg/cmd
)
