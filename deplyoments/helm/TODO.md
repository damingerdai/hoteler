# Hoteler Helm Chart 待办

记录代码审查中发现但尚未修复的问题，按优先级排列。

## 发布前建议确认

- [ ] 镜像 tag 前缀：`values.yaml` 里 `image.tag` / `image.web.tag` 是 `0.0.7`，但 GHCR 上实际发布的是 `v0.0.7`（无 `v` 前缀的 tag 不存在）。需统一为 `v0.0.7`，或在 release workflow 里同时打无前缀 tag，否则默认安装会 `ImagePullBackOff`。
- [ ] `ghcr.io/damingerdai/hoteler-api` 是私有包，chart 目前没有 `imagePullSecrets` 支持；集群拉取镜像前需要配置。
- [ ] 全新环境没有数据库表：`config.yaml` 里 `spring.flyway.enabled: false`，chart 中也没有迁移 Job。应增加 `pre-install` / `pre-upgrade` 迁移 Job，或把 flyway 开关做成 values 配置。

## 安全与运行时

- [ ] `values.yaml` 里没有 `securityContext`，而 `deployment.yaml` 用 `with` 引用它，因此被静默跳过：api 容器默认以 root 运行，web 容器完全没有安全上下文。建议补 `runAsNonRoot`、`allowPrivilegeEscalation: false`、`readOnlyRootFilesystem`，并设置 `automountServiceAccountToken: false`。
- [ ] 不支持 `imagePullSecrets`、`nodeSelector`、`tolerations`、`affinity`、`podAnnotations`、`terminationGracePeriodSeconds` 和滚动更新 `strategy`。
- [ ] `resources` 硬编码在模板里（api 200m/500Mi → 500m/1536Mi，web 100m/128Mi → 200m/256Mi），应移到 values。
- [ ] `mybatis.configuration.log-impl: StdOutImpl` 会打印全量 SQL，生产配置建议去掉或加开关。
- [ ] `secret.existingSecret` 的内容由外部管理，Helm 感知不到变更（`checksum/secret` 只覆盖 values）；修改外部 Secret 后需要手动重启 Pod。
- [ ] api Service 默认 `NodePort: 30006`，明文 HTTP 绕过 nginx；建议默认 `ClusterIP`，NodePort 改为可选。另外 30006/30007 与 `deplyoments/k3s/service.yaml` 冲突（NodePort 是集群级资源）。

## Ingress / 网络

- [ ] Ingress 没有 `ingressClassName`、TLS、annotations，也没有 `enabled` 开关，无法按环境关闭。
- [ ] web Service 的 `type: NodePort` 和 port 80 硬编码，没有跟随 `service.type`。

## Chart 规范

- [ ] `app.namespace` 是死值，模板实际使用 `.Release.Namespace`；删除或接上。
- [ ] `replicaCount` 只作用于 api；web Deployment 的 `replicas: 1` 硬编码，web 也没有 HPA。
- [ ] 缺少标准标签 `app.kubernetes.io/*`、`helm.sh/chart`、`app.kubernetes.io/managed-by`；HPA 的 `app: xxx-hpa` 命名不统一。
- [ ] 缺少 `templates/NOTES.txt`、`values.schema.json`；`Chart.yaml` 缺 `home`、`sources`、`maintainers`、`kubeVersion`、`icon`（`helm lint` 会提示 icon）。
- [ ] `secret.existingSecret` 要求的键名 `secret-key` / `redis-password` / `datasource-password` 写死在模板里，不支持自定义键名。
- [ ] `redis.ssl | default false` 写法，以及 `flyway.baseline-on-migrate`（flyway 已禁用）属于冗余配置，建议整理。

## 相关清单与发布流程

- [ ] `deplyoments/k3s/job.yaml` 里的 CronJob 直接访问 `http://hoteler-api:8443/ping`。资源名加上 release 前缀后，只有 release 名为 `hoteler` 才保持原名，其他 release 名需要同步修改该地址。
- [ ] `Chart.yaml` 版本、`README.md` 安装示例版本、发布 workflow 必须一起维护。本次已对齐到 0.0.6，后续发版注意同步。
