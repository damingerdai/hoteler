module github.com/damingerdai/hoteler/migration

go 1.19

require (
	github.com/golang-migrate/migrate/v4 v4.15.2
	github.com/jackc/pgx/v4 v4.12.1-0.20210724153913-640aa07df17c
	github.com/jackc/pgx/v5 v5.2.0
	github.com/joho/godotenv v1.4.0
	github.com/spf13/cobra v1.6.1
)

require (
	github.com/hashicorp/errwrap v1.1.0 // indirect
	github.com/hashicorp/go-multierror v1.1.1 // indirect
	github.com/inconshreveable/mousetrap v1.0.1 // indirect
	github.com/jackc/chunkreader/v2 v2.0.1 // indirect
	github.com/jackc/pgconn v1.13.0 // indirect
	github.com/jackc/pgio v1.0.0 // indirect
	github.com/jackc/pgpassfile v1.0.0 // indirect
	github.com/jackc/pgproto3/v2 v2.3.1 // indirect
	github.com/jackc/pgservicefile v0.0.0-20200714003250-2b9c44734f2b // indirect
	github.com/jackc/pgtype v1.12.0 // indirect
	github.com/jackc/puddle v1.3.0 // indirect
	github.com/lib/pq v1.10.2 // indirect
	github.com/spf13/pflag v1.0.5 // indirect
	go.uber.org/atomic v1.10.0 // indirect
	golang.org/x/crypto v0.0.0-20220829220503-c86fa9a7ed90 // indirect
	golang.org/x/text v0.3.8 // indirect
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
