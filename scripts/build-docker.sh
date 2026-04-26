#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Get the current commit SHA
SHA=$(git rev-parse --short HEAD)

# Define the Docker image name
image_name="hoteler-api"
# Define the target platforms (AMD64 for servers, ARM64 for Apple Silicon/Raspberry Pi)
PLATFORMS="linux/amd64,linux/arm64"

echo "Starting multi-platform build for: ${PLATFORMS}"

# --- 1. Prepare Buildx ---
# Check if the 'multi-arch-builder' instance exists, if not, create it
if ! docker buildx inspect multi-arch-builder > /dev/null 2>&1; then
    echo "Creating new buildx builder instance..."
    docker buildx create --name multi-arch-builder --use
fi

# Ensure the builder is started and ready
docker buildx inspect --bootstrap

# --- 2. Build Process ---
# Note: '--load' exports the image to the local Docker daemon.
# However, Docker's default storage engine doesn't support multi-arch manifests locally.
# If you only want to build and test locally, we build for the current host's arch.
# If you want to build BOTH and keep them, we use the '--platform' flag.

echo "Building Docker image: ${IMAGE_NAME}:${SHA}..."

docker buildx build \
  --platform ${PLATFORMS} \
  -t ${IMAGE_NAME}:${SHA} \
  -t ${IMAGE_NAME}:latest \
  --file Dockerfile \
  --output type=docker \
  .

# --- 3. Verification ---
echo "-----------------------------------------------"
echo "✅ Build completed successfully!"
echo "Image Tags:"
echo "  - ${IMAGE_NAME}:${SHA}"
echo "  - ${IMAGE_NAME}:latest"
echo "-----------------------------------------------"

# Display the architecture of the built image
docker inspect ${IMAGE_NAME}:${SHA} | grep -i 'Architecture' || true

