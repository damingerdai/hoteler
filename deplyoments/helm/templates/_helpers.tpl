{{/* 资源名带 release 前缀，同一 namespace 可以安装多个 release */}}
{{- define "hoteler.api.fullname" -}}
{{- printf "%s-%s" .Release.Name .Values.app.name | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "hoteler.web.fullname" -}}
{{- printf "%s-%s" .Release.Name .Values.app.web.name | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "hoteler.api.configName" -}}
{{- printf "%s-%s" .Release.Name .Values.app.configMapName | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "hoteler.web.configName" -}}
{{- printf "%s-%s" .Release.Name .Values.app.web.configMapName | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "hoteler.secretName" -}}
{{- printf "%s-%s" .Release.Name .Values.secret.name | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/* 配置或密钥变化时触发 Pod 滚动更新 */}}
{{- define "hoteler.configChecksum" -}}
{{- include (print $.Template.BasePath "/config.yaml") $ | sha256sum -}}
{{- end -}}

{{- define "hoteler.secretChecksum" -}}
{{- printf "%v|%v|%v|%v|%v" $.Values.secret.name $.Values.secret.existingSecret $.Values.secret.key $.Values.redis.password $.Values.datasource.password | sha256sum -}}
{{- end -}}
