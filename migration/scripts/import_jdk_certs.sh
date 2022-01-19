
#!/usr/bin/env bash

set -Eeuo pipefail

script_dir=$(cd "$(dirname "${BASH_SOURCE[0]}")" &>/dev/null && pwd -P)

files=(www.github.com mirror.bazel.build objects.githubusercontent.com)

for file in ${files[@]};do
  keytool -import -v -trustcacerts -alias ${file} -file "$script_dir/${file}.crt" -storepass changeit -keystore "$script_dir/cacerts"
done