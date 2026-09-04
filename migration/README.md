# Hoteler的数据库迁移

Hoteler的数据库迁移模块基于[migrate](https://github.com/golang-migrate/migrate)。

## 使用

先编译：

```shell
make build
```

升级

```shell
./hoteler migrate up
```

降级

```shell
./hoteler migrate down
```

修复

```shell
./hoteler migrate force <version>
```

新建数据库迁移脚本

```shell
./hoteler migrate create sql-file-name
```

查看数据库迁移脚本

```shell
./hoteler migrate list
```

在列表中使用方向键或 `j`/`k` 移动，使用 `q` 或 `Esc` 退出。

运行测试：

```shell
make test
```
