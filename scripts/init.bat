@echo off
echo "create docker network 'hoteler-network'"
docker network create hoteler-network
echo "create docker volume 'hoteler-volume'"
docker volume create hoteler-volume
pause