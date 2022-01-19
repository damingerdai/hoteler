#!/bin/bash

set -Eeuo pipefail

script_dir=$(cd "$(dirname "${BASH_SOURCE[0]}")" &>/dev/null && pwd -P)

hosts=(github.com mirror.bazel.build objects.githubusercontent.com)
port=443

for host in ${hosts[@]};do
  echo "Q"|openssl s_client -connect ${host}:${port} -servername ${host} -showcerts|openssl x509 -outform pem > "$script_dir/${host}.crt"
done