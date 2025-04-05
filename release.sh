#!/bin/bash
set -e

# 获取当前版本并提取基础版本号
CURRENT_VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
BASE_VERSION=$(echo "$CURRENT_VERSION" | sed -E 's/-.*$//')

# 解析语义化版本号
IFS='.' read -ra VER_PARTS <<< "$BASE_VERSION"
MAJOR=${VER_PARTS[0]:-0}
MINOR=${VER_PARTS[1]:-0}
PATCH=${VER_PARTS[2]:-0}

# 版本类型选择菜单
PS3='请选择版本更新类型: '
options=("major (${MAJOR+1}.0.0)" "minor (${MAJOR}.$((MINOR+1)).0)" "patch (${MAJOR}.${MINOR}.$((PATCH+1)))")
select opt in "${options[@]}"
do
    case $REPLY in
        1)
            NEW_VERSION="$((MAJOR+1)).0.0"
            break
            ;;
        2)
            NEW_VERSION="${MAJOR}.$((MINOR+1)).0"
            break
            ;;
        3)
            NEW_VERSION="${MAJOR}.${MINOR}.$((PATCH+1))"
            break
            ;;
        *)
            echo "无效选项，请重新选择"
            ;;
    esac
done

# 用户确认环节
echo "当前版本: $CURRENT_VERSION"
echo "新版本号: $NEW_VERSION"
read -p "确认升级并发布? [Y/n] " REPLY
if [[ $REPLY =~ ^[Nn]$ ]]; then
    echo "操作已取消"
    exit 0
fi

# 执行版本升级
mvn versions:set -DnewVersion="$NEW_VERSION" -DgenerateBackupPoms=false

# 提交版本变更
git add pom.xml
git commit -m "chore(release): prepare version $NEW_VERSION"

# 生成变更日志
mvn git-changelog-maven-plugin:git-changelog -Dchangelog.range="v$BASE_VERSION..HEAD"
git add CHANGELOG.md
git commit -m "chore(release): v$NEW_VERSION"

# 创建带注释的标签
git tag -a "v$NEW_VERSION" -m "Release version $NEW_VERSION" \
    -m "包含以下变更:" -m "$(git log --pretty=format:%s 'v$BASE_VERSION..HEAD')"

# 推送变更
git push origin main && git push origin "v$NEW_VERSION"

echo "成功发布版本 v$NEW_VERSION!"