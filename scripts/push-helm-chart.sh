#!/usr/bin/env bash

set -euo pipefail

# Push the Helm chart to GitHub Container Registry (GHCR).
#
# Local usage:
#   GHCR_TOKEN=<github_pat> GITHUB_USERNAME=<github_user> \
#     ./scripts/push-helm-chart.sh
#
# Override the chart version when needed:
#   GHCR_TOKEN=<github_pat> GITHUB_USERNAME=<github_user> \
#     ./scripts/push-helm-chart.sh 0.0.6
#
# GitHub Actions usage:
#   GITHUB_TOKEN is accepted as a fallback for GHCR_TOKEN.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "${SCRIPT_DIR}/.." && pwd)"
CHART_DIR="${PROJECT_ROOT}/deplyoments/helm"
REGISTRY="${HELM_REGISTRY:-ghcr.io}"
OWNER="${GHCR_OWNER:-${GITHUB_REPOSITORY_OWNER:-damingerdai}}"
USERNAME="${GITHUB_USERNAME:-${GITHUB_ACTOR:-}}"
TOKEN="${GHCR_TOKEN:-${GITHUB_TOKEN:-}}"

if [ $# -gt 1 ]; then
    echo "Usage: $0 [chart-version]"
    exit 1
fi

for command in helm awk mktemp; do
    if ! command -v "${command}" >/dev/null 2>&1; then
        echo "Required command not found: ${command}"
        exit 1
    fi
done

if [ ! -f "${CHART_DIR}/Chart.yaml" ]; then
    echo "Chart.yaml not found: ${CHART_DIR}/Chart.yaml"
    exit 1
fi

if [ -z "${USERNAME}" ]; then
    echo "Set GITHUB_USERNAME (or GITHUB_ACTOR)."
    exit 1
fi

if [ -z "${TOKEN}" ]; then
    echo "Set GHCR_TOKEN (or GITHUB_TOKEN)."
    exit 1
fi

# Use the chart version from Chart.yaml unless an explicit version is supplied.
CHART_VERSION="${1:-$(awk '$1 == "version:" {gsub(/"/, "", $2); gsub(/\047/, "", $2); print $2; exit}' "${CHART_DIR}/Chart.yaml")}"
if [ -z "${CHART_VERSION}" ]; then
    echo "Could not determine chart version from ${CHART_DIR}/Chart.yaml"
    exit 1
fi

# GHCR image/package names must be lowercase.
OWNER="$(printf '%s' "${OWNER}" | tr '[:upper:]' '[:lower:]')"
PACKAGE_DIR="$(mktemp -d)"
trap 'rm -rf "${PACKAGE_DIR}"' EXIT

echo "Linting Helm chart..."
helm lint "${CHART_DIR}"

echo "Packaging ${CHART_DIR} version ${CHART_VERSION}..."
helm package "${CHART_DIR}" \
    --version "${CHART_VERSION}" \
    --destination "${PACKAGE_DIR}"

echo "Logging in to ${REGISTRY}..."
printf '%s' "${TOKEN}" | helm registry login "${REGISTRY}" \
    --username "${USERNAME}" \
    --password-stdin

CHART_NAME="$(awk '$1 == "name:" {gsub(/"/, "", $2); gsub(/\047/, "", $2); print $2; exit}' "${CHART_DIR}/Chart.yaml")"
PACKAGE_FILE="${PACKAGE_DIR}/${CHART_NAME}-${CHART_VERSION}.tgz"
if [ ! -f "${PACKAGE_FILE}" ]; then
    echo "Packaged chart not found: ${PACKAGE_FILE}"
    exit 1
fi

TARGET="oci://${REGISTRY}/${OWNER}"
echo "Pushing ${PACKAGE_FILE} to ${TARGET}..."
helm push "${PACKAGE_FILE}" "${TARGET}"

echo "Helm chart pushed successfully: ${TARGET}/$(basename "${PACKAGE_FILE}" .tgz)"
