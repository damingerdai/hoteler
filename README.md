# hoteler
一个简单的入门酒店管理系统

# 系统功能：
1. 登录验证
2. 房态统计显示
3. 预定登记并入库
4. 入住登记并入库
5. 换房登记并入库
6. 客人信息查询
7. 退房并入库

# 初始化

## Docker环境
1. 创建数据卷
```bash
docker volume create --name=hoteler-volume
```
2. 创建网桥
```bash
docker network create hoteler-network
```
3. 启动
```bash
docker-compose up
```