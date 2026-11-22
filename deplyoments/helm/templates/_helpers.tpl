{{/* 配置或密钥变化时触发 Pod 滚动更新 */}}
{{- define "hoteler.configChecksum" -}}
{{- include (print $.Template.BasePath "/config.yaml") $ | sha256sum -}}
{{- end -}}

{{- define "hoteler.secretChecksum" -}}
{{- printf "%v|%v|%v|%v|%v" $.Values.secret.name $.Values.secret.existingSecret $.Values.secret.key $.Values.redis.password $.Values.datasource.password | sha256sum -}}
{{- end -}}
