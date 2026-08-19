#!/usr/bin/env bash

set -euo pipefail

# ------------------------------------------------------------
# Hoteler Release Script
#
# Usage:
#   ./scripts/release.sh 0.0.5
#   ./scripts/release.sh v0.0.5
#
# It will:
#   1. Validate environment and git state
#   2. Update build.gradle
#   3. Update pom.xml
#   4. Generate changelog with git-cliff
#   5. Commit release files
#   6. Create git tag
# ------------------------------------------------------------

if [ $# -ne 1 ]; then
    echo "Usage: $0 <version>"
    echo ""
    echo "Examples:"
    echo "  $0 0.0.5"
    echo "  $0 v0.0.5"
    exit 1
fi

INPUT_VERSION="$1"

# Allow both 0.0.5 and v0.0.5
VERSION="${INPUT_VERSION#v}"
TAG="v${VERSION}"

BUILD_GRADLE="build.gradle"
POM_XML="pom.xml"
CHANGELOG="CHANGELOG.md"

echo "Preparing release ${TAG}"
echo ""

# ------------------------------------------------------------
# Validate semantic version
# ------------------------------------------------------------

if [[ ! "$VERSION" =~ ^[0-9]+\.[0-9]+\.[0-9]+([.-][0-9A-Za-z.-]+)?$ ]]; then
    echo "Invalid version: ${VERSION}"
    echo "Expected something like: 0.0.5"
    exit 1
fi

# ------------------------------------------------------------
# Check required commands
# ------------------------------------------------------------

for command in git git-cliff python3; do
    if ! command -v "$command" >/dev/null 2>&1; then
        echo "Required command not found: ${command}"
        exit 1
    fi
done

# ------------------------------------------------------------
# Check required files
# ------------------------------------------------------------

for file in "$BUILD_GRADLE" "$POM_XML"; do
    if [ ! -f "$file" ]; then
        echo "Required file not found: ${file}"
        exit 1
    fi
done

# ------------------------------------------------------------
# Ensure working tree is clean
# ------------------------------------------------------------

if [ -n "$(git status --porcelain)" ]; then
    echo "Git working tree is not clean."
    echo ""
    git status --short
    echo ""
    echo "Commit or stash your changes before releasing."
    exit 1
fi

# ------------------------------------------------------------
# Ensure tag does not already exist
# ------------------------------------------------------------

if git rev-parse "$TAG" >/dev/null 2>&1; then
    echo "Tag already exists: ${TAG}"
    exit 1
fi

# ------------------------------------------------------------
# Read current versions
# ------------------------------------------------------------

CURRENT_GRADLE_VERSION="$(
python3 <<'PY'
import re

content = open("build.gradle", encoding="utf-8").read()

match = re.search(
    r"(?m)^\s*version\s*=\s*['\"]([^'\"]+)['\"]",
    content
)

if not match:
    raise SystemExit("Cannot find project version in build.gradle")

print(match.group(1))
PY
)"

CURRENT_MAVEN_VERSION="$(
python3 <<'PY'
import re

content = open("pom.xml", encoding="utf-8").read()

match = re.search(
    r"<artifactId>\s*hoteler\s*</artifactId>\s*"
    r"<version>\s*([^<]+)\s*</version>",
    content,
)

if not match:
    raise SystemExit("Cannot find project version in pom.xml")

print(match.group(1).strip())
PY
)"

echo "Current Gradle version: ${CURRENT_GRADLE_VERSION}"
echo "Current Maven version:  ${CURRENT_MAVEN_VERSION}"
echo "Target version:         ${VERSION}"
echo ""

# ------------------------------------------------------------
# Ensure Maven and Gradle versions are synchronized
# ------------------------------------------------------------

if [ "$CURRENT_GRADLE_VERSION" != "$CURRENT_MAVEN_VERSION" ]; then
    echo "Version mismatch detected:"
    echo "  build.gradle: ${CURRENT_GRADLE_VERSION}"
    echo "  pom.xml:      ${CURRENT_MAVEN_VERSION}"
    echo ""
    echo "Please synchronize them before releasing."
    exit 1
fi

if [ "$CURRENT_GRADLE_VERSION" = "$VERSION" ]; then
    echo "Project is already version ${VERSION}"
    exit 1
fi

# ------------------------------------------------------------
# Update build.gradle and pom.xml
# ------------------------------------------------------------

VERSION="$VERSION" python3 <<'PY'
import os
import re
from pathlib import Path

version = os.environ["VERSION"]

# ------------------------------------------------------------
# build.gradle
# ------------------------------------------------------------

gradle_path = Path("build.gradle")
gradle = gradle_path.read_text(encoding="utf-8")

gradle, count = re.subn(
    r"(?m)^(\s*version\s*=\s*)['\"][^'\"]+['\"]",
    rf"\1'{version}'",
    gradle,
    count=1,
)

if count != 1:
    raise SystemExit(
        f"Expected to update exactly one project version in build.gradle, got {count}"
    )

gradle_path.write_text(gradle, encoding="utf-8")

# ------------------------------------------------------------
# pom.xml
#
# Only replace the project version immediately following:
# <artifactId>hoteler</artifactId>
#
# This deliberately does NOT touch dependency/plugin versions.
# ------------------------------------------------------------

pom_path = Path("pom.xml")
pom = pom_path.read_text(encoding="utf-8")

pattern = re.compile(
    r"(<artifactId>\s*hoteler\s*</artifactId>\s*"
    r"<version>\s*)"
    r"[^<]+"
    r"(\s*</version>)"
)

pom, count = pattern.subn(
    rf"\g<1>{version}\g<2>",
    pom,
    count=1,
)

if count != 1:
    raise SystemExit(
        f"Expected to update exactly one project version in pom.xml, got {count}"
    )

pom_path.write_text(pom, encoding="utf-8")

print(f"Updated project version to {version}")
PY

# ------------------------------------------------------------
# Generate changelog
# ------------------------------------------------------------

echo ""
echo "Generating changelog for ${TAG}..."

git-cliff \
    --unreleased \
    --tag "$TAG" \
    --prepend "$CHANGELOG"

# ------------------------------------------------------------
# Show changes
# ------------------------------------------------------------

echo ""
echo "Release changes:"
echo "------------------------------------------------------------"

git diff -- "$BUILD_GRADLE" "$POM_XML" "$CHANGELOG"

echo "------------------------------------------------------------"
echo ""

# ------------------------------------------------------------
# Commit release
# ------------------------------------------------------------

git add "$BUILD_GRADLE" "$POM_XML" "$CHANGELOG"

git commit -m "chore(release): ${TAG}"

# ------------------------------------------------------------
# Create annotated tag
# ------------------------------------------------------------

git tag -a "$TAG" -m "Release ${TAG}"

echo ""
echo "Release ${TAG} created successfully."
echo ""
echo "Commit:"
git log -1 --oneline

echo ""
echo "Tag:"
git tag -n1 "$TAG"

echo ""
echo "Nothing has been pushed yet."
echo ""
echo "Review the release and then run:"
echo ""
echo "  git push origin HEAD"
echo "  git push origin ${TAG}"
echo ""