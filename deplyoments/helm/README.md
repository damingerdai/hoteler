# Hoteler Helm Chart 使用说明

这个 Helm Chart 发布在 GitHub Container Registry（GHCR）：

```text
oci://ghcr.io/damingerdai/hoteler-chart
```

## 前置条件

- Kubernetes 集群已运行
- 已安装 Helm 3+
- 集群可以访问 `ghcr.io`
- PostgreSQL 和 Redis 已运行，并且 Kubernetes Service 名称分别为 `postgres` 和 `redis`

## 登录 GHCR

如果 chart 是私有的，需要使用 GitHub classic Personal Access Token（PAT）登录。PAT 至少需要 `read:packages` 权限。

```bash
export GHCR_TOKEN=<your-github-pat>

echo "$GHCR_TOKEN" | helm registry login ghcr.io \
  --username damingerdai \
  --password-stdin
```

如果 chart 是公开的，通常可以直接安装，不需要登录。

## 安装

使用 chart 的指定版本安装：

```bash
helm install hoteler \
  oci://ghcr.io/damingerdai/hoteler-chart \
  --version 0.0.5 \
  --namespace hoteler-namespace \
  --create-namespace
```

`hoteler` 是 Helm release 名称，可以按需修改。

## 查看安装结果

```bash
helm list --namespace hoteler-namespace

kubectl get pods \
  --namespace hoteler-namespace

kubectl get svc \
  --namespace hoteler-namespace
```

默认情况下，后端 Service 使用 NodePort `30006`，前端 Service 使用 NodePort `30007`。

## 使用自定义配置安装

复制一份配置文件并按实际环境修改：

```bash
cp values.yaml my-values.yaml
```

至少应修改数据库、Redis 和镜像配置：

```yaml
image:
  repository: ghcr.io/damingerdai/hoteler
  tag: "0.0.5"
  web:
    repository: ghcr.io/damingerdai/hoteler-web
    tag: "0.0.5"

redis:
  host: redis
  port: 6379
  database: 0
  password: <redis-password>

datasource:
  url: jdbc:postgresql://postgres:5432/postgres
  username: <postgres-user>
  password: <postgres-password>
```

使用自定义配置安装：

```bash
helm install hoteler \
  oci://ghcr.io/damingerdai/hoteler-chart \
  --version 0.0.5 \
  --namespace hoteler-namespace \
  --create-namespace \
  --values my-values.yaml
```

不要将包含真实密码的 values 文件提交到 Git 仓库。

## 升级

`helm upgrade --install` 可以同时兼容首次安装和升级：

```bash
helm upgrade --install hoteler \
  oci://ghcr.io/damingerdai/hoteler-chart \
  --version 0.0.5 \
  --namespace hoteler-namespace \
  --create-namespace \
  --values my-values.yaml
```

也可以只覆盖镜像版本：

```bash
helm upgrade hoteler \
  oci://ghcr.io/damingerdai/hoteler-chart \
  --version 0.0.5 \
  --namespace hoteler-namespace \
  --set image.tag=0.0.5 \
  --set image.web.tag=0.0.5
```

## 安装前检查模板

查看最终生成的 Kubernetes YAML：

```bash
helm template hoteler \
  oci://ghcr.io/damingerdai/hoteler-chart \
  --version 0.0.5 \
  --namespace hoteler-namespace \
  --values my-values.yaml
```

执行 Helm 预检查：

```bash
helm upgrade --install hoteler \
  oci://ghcr.io/damingerdai/hoteler-chart \
  --version 0.0.5 \
  --namespace hoteler-namespace \
  --values my-values.yaml \
  --dry-run
```

## 卸载

```bash
helm uninstall hoteler \
  --namespace hoteler-namespace
```

如需删除 namespace：

```bash
kubectl delete namespace hoteler-namespace
```

## 发布新版本

在 GitHub Actions 中打开 **Actions → Push Helm chart → Run workflow**，填写 chart 版本后手动发布。

发布成功后，将版本号替换为新版本即可安装，例如：

```bash
helm upgrade --install hoteler \
  oci://ghcr.io/damingerdai/hoteler-chart \
  --version 0.0.6 \
  --namespace hoteler-namespace \
  --values my-values.yaml
```
