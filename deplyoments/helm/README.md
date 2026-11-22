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
  --version 0.0.6 \
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

资源名称格式为 `<release 名>-<名称后缀>`。使用 `helm install hoteler ...` 时默认得到 `hoteler-api`、`hoteler-web`、`hoteler-api-config` 等；换成其他 release 名（例如 `staging`）会得到 `staging-api`、`staging-web`，因此可以在同一个 namespace 中安装多套环境。名称后缀可通过 `app.name`、`app.web.name`、`app.configMapName`、`app.web.configMapName`、`secret.name` 修改。

## 使用自定义配置安装

复制一份配置文件并按实际环境修改：

```bash
cp values.yaml my-values.yaml
```

至少应修改数据库、Redis 和镜像配置：

```yaml
image:
  repository: ghcr.io/damingerdai/hoteler-api
  tag: "0.0.7"
  web:
    repository: ghcr.io/damingerdai/hoteler-web
    tag: "0.0.7"

redis:
  host: redis
  port: 6379
  database: 0
  password: <redis-password>

datasource:
  url: jdbc:postgresql://postgres:5432/postgres
  username: <postgres-user>
  password: <postgres-password>

secret:
  # 留空时首次安装自动生成随机签名密钥，升级时自动复用
  key: ""
  # 或者复用已有 Secret，设置后不再创建 Secret。
  # Secret 需包含 secret-key、redis-password、datasource-password 三个键。
  existingSecret: ""
```

签名密钥用于签发 JWT 和校验用户令牌，修改后已签发的令牌会失效，用户需要重新登录；如需固定密钥，请显式设置 `secret.key` 或使用 `secret.existingSecret`。

数据库和 Redis 密码不会写入 ConfigMap，而是保存到 Secret（默认名称 `hoteler-api-secret`，可用 `secret.name` 修改），再以 `SPRING_DATASOURCE_PASSWORD`、`SPRING_DATA_REDIS_PASSWORD` 环境变量注入。生产环境建议通过 `--set`、私有 values 文件或 `secret.existingSecret` 提供密码。

使用自定义配置安装：

```bash
helm install hoteler \
  oci://ghcr.io/damingerdai/hoteler-chart \
  --version 0.0.6 \
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
  --version 0.0.6 \
  --namespace hoteler-namespace \
  --create-namespace \
  --values my-values.yaml
```

也可以只覆盖镜像版本：

```bash
helm upgrade hoteler \
  oci://ghcr.io/damingerdai/hoteler-chart \
  --version 0.0.6 \
  --namespace hoteler-namespace \
  --set image.tag=0.0.7 \
  --set image.web.tag=0.0.7
```

## 水平扩容（可选）

HPA 默认关闭。开启时需要集群已安装 metrics-server，并根据实际情况调大 `replicaCount.max`；开启后 Deployment 不再设置 `replicas`，副本数完全由 HPA 管理：

```yaml
hpa:
  enabled: true
  replicaCount:
    min: 1
    max: 3
```

## 安装前检查模板

查看最终生成的 Kubernetes YAML：

```bash
helm template hoteler \
  oci://ghcr.io/damingerdai/hoteler-chart \
  --version 0.0.6 \
  --namespace hoteler-namespace \
  --values my-values.yaml
```

执行 Helm 预检查：

```bash
helm upgrade --install hoteler \
  oci://ghcr.io/damingerdai/hoteler-chart \
  --version 0.0.6 \
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

先更新 `Chart.yaml` 中的 `version`（必要时同步 `appVersion` 和本 README 的安装示例），然后在 GitHub Actions 中打开 **Actions → Push Helm chart → Run workflow** 手动发布。输入框可留空（自动读取 Chart.yaml）；如果填写，必须与 `Chart.yaml` 中的版本一致，否则 workflow 会失败。

发布成功后，将版本号替换为新版本即可安装，例如：

```bash
helm upgrade --install hoteler \
  oci://ghcr.io/damingerdai/hoteler-chart \
  --version 0.0.7 \
  --namespace hoteler-namespace \
  --values my-values.yaml
```
