
#!/usr/bin/env bash

set -Eeuo pipefail

script_dir=$(cd "$(dirname "${BASH_SOURCE[0]}")" &>/dev/null && pwd -P)
java_dir="/Users/gming001/Software/zulu17.40.19-ca-jdk17.0.6-macosx_aarch64/lib/security"

files=(www.github.com mirror.bazel.build objects.githubusercontent.com dl.google.com)

for file in ${files[@]};do
  sudo keytool -import -v -trustcacerts -alias ${file} -file "$script_dir/${file}.crt" -storepass changeit -keystore "$java_dir/cacerts"
done