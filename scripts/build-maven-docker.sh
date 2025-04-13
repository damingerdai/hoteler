#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Get the current commit SHA
sha=$(git rev-parse HEAD)

# Define the Docker image name
image_name="hoteler-api"

# Build the Docker image with the current commit SHA as the tag
docker build --platform linux/amd64,linux/arm64 -t ${image_name}:${sha} -f maven.Dockerfile .

# Print a success message
echo "Docker image ${image_name}:${sha} built successfully."
