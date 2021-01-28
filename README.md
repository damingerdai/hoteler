# hoteler
一个简单的入门酒店管理系统

## 系统功能：
1. 登录验证
2. 房态统计显示
3. 预定登记并入库
4. 入住登记并入库
5. 换房登记并入库
6. 客人信息查询
7. 退房并入库

## 初始化

### 克隆仓库

```shell script
git clone git@github.com:damingerdai/hoteler.git --recurse-submodules --remote-submodules
```

### Docker环境

1. 创建数据卷

```shell script
docker volume create --name=hoteler-volume
```
2. 创建网桥

```shell script
docker network create hoteler-network
```
3. 启动数据库

```shell script
docker-compose up
```

### 数据库迁移

```shell script
mvn flyway:migrate
```

### 构建

```shell script
sh scripts/build.sh
```

## 使用

### 浏览器

请访问：[http://localhost:8443](http://localhost:8080)

默认的账号密码： admin/12345
### swagger-ui

后端支持OpenApi, 请访问: [http://localhost:8443/swagger-ui/index.html](http://localhost:8443/swagger-ui/index.html)

### graphql

由于graphql的集成存在bug，因此已经废弃。
<!-- [http://localhost:8080/graphiq](http://localhost:8080/graphiq) -->