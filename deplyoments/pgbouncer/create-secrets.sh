#!/bin/sh
KUBE_NAMESPACE="hoteler-namespace"
cd `dirname $0`
for file in *.ini
do
  basename="$(basename $file)"
  echo $basname
  echo $file
  kubectl create secret generic "${basename%.*}"-config --namespace="$KUBE_NAMESPACE" --from-file="$file"  --from-file=userlist.txt -o yaml --dry-run=client | tee "${basename%.*}.yaml"
done