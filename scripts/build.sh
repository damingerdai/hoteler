#!/bin/bash

# Set error flags
set -o nounset
set -o errexit
set -o pipefail

echo "Building web application..."

# Navigate to the Angular project directory, build the project, and copy the build output
(
  cd src/main/angular
  yarn
  yarn ng build
  cp -r dist/hoteler/browser/* ../resources/static/
)

echo "Copying web application to resources..."

# Create an error directory and copy index.html into it as 404.html
(
  cd src/main/resources/static
  mkdir -p error
  cp index.html error/404.html
)

echo "Building Java application with Gradle..."

# Build the Java application with Gradle
gradle build -Pstandalone -x test