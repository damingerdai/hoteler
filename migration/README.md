# Hoteler的数据库迁移

Hoteler的数据库迁移模块基于[migrate](https://github.com/golang-migrate/migrate)。

## 使用

升级

```shell
go build -o migrate migration.go && ./migrate(?.exe) up
```

降级

```shell
go build -o migrate migration.go && ./migrate(?.exe) down
```

修复

```shell
go build -o migrate migration.go && ./migrate(?.exe) force
```

新建数据库迁移脚本

```shell
go build -o migrate migration.go && ./migrate(?.exe) create sql-file-name
```