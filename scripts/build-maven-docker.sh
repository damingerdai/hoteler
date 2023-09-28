#!/bin/bash

sha=$(git rev-parse HEAD)

docker build -t hoteler-maven-api:${sha} -f maven.Dockerfile .