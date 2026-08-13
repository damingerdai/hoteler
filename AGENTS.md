# Repository Guidelines

## Project Structure & Module Organization

The main application is a Spring Boot Java service in `src/main/java/org/daming/hoteler`, organized by configuration, controllers, services, repositories, security, and domain objects. Runtime configuration, Flyway SQL migrations, GraphQL schemas, and other resources live in `src/main/resources`. Java/JUnit tests are in `src/test/java`, with test configuration in `src/test/resources`. The Angular client is under `src/main/angular`; deployment manifests are in `deplyoments`, and Docker/operational helpers are in `scripts`. The independent Go migration tool is in `migration`.

## Build, Test, and Development Commands

Use the Gradle wrapper so the local Gradle version matches the project:

- `./gradlew build` compiles, tests, runs checks, and packages the Spring Boot application.
- `./gradlew test` runs the JUnit Platform test suite.
- `./gradlew bootRun` starts the backend locally.
- `./gradlew checkstyleMain checkstyleTest -PenableCheckstyle=true` runs the configured Checkstyle checks.
- `docker compose up db redis` starts the PostgreSQL and Redis dependencies.
- `cd migration && go test ./...` runs migration-tool tests; `go build -o migrate migration.go` builds it.
- `./scripts/build.sh` builds the Angular frontend and a standalone backend artifact; use it when frontend assets must be packaged.

Local integration work generally needs PostgreSQL on port `5432` and Redis on `6379`. Configure credentials through environment/config files; do not commit secrets.

## Coding Style & Naming Conventions

Follow the existing Java style and `config/checkstyle.xml`; use four-space indentation, conventional Java braces, and descriptive names. Packages are lowercase; classes/interfaces use `PascalCase`, methods and fields use `camelCase`, and interfaces commonly use the project’s existing `I...` naming. Keep SQL migration filenames in the existing versioned `V<timestamp>__<description>.sql` format. Format Go changes with `gofmt` (or `make fmt` in `migration`).

## Testing Guidelines

Add JUnit tests beside the relevant package under `src/test/java`, naming classes with the `Test` suffix. Go tests use `_test.go` and package-local coverage. Run the focused test first, then the full `./gradlew test` suite; include tests for new behavior and explain omissions in the pull request.

## Commit & Pull Request Guidelines

Use short Conventional Commit-style subjects, such as `fix(deps): ...`, `chore: ...`, or `ci: ...`, optionally referencing an issue/PR. Pull requests should explain the purpose and implementation, complete `.github/pull_request_template.md`, describe test commands/results, disclose dependency changes, and include screenshots for UI changes. Keep migrations, configuration, and deployment changes clearly called out.
