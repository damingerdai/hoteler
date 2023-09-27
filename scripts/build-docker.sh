#!/bin/bash

sha=$(git rev-parse HEAD)

docker build -t hoteler-api:${sha} .