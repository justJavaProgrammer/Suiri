#!/bin/bash
# Bash script to bootstrap docker container with Suiri bot
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
RED='\033[0;31m'
RESET_COLOR="\e[0m"

function logInfo() {
  echo -e "[${GREEN} INFO ${RESET_COLOR}] $1"
}

function logWarn() {
  echo -e "[${YELLOW} WARN ${RESET_COLOR}] $1 "
}

function logError() {
  echo -e "[${RED} ERROR ${RESET_COLOR}] $1"
}

function createImage() {
  docker image build -t "$docker_image_name" .
}

docker container stop suiri_db
docker container rm suiri_db
docker container stop suiribot_container

docker_image_name=suiribot

logInfo "Starting building the docker image with name: $docker_image_name"

if docker image ls | grep -q $docker_image_name; then
  read -p "$(logWarn "Image with name: $docker_image_name already exist. Reuse existing image(y) or recreate(n): ")" -n 1 -r
  echo ""
  if [[ $REPLY =~ ^[Yy]$ ]]; then
    logInfo "Reusing existing image"

  else
    logInfo "Recreate image with name $docker_image_name"
    docker image rm -f $docker_image_name
    if $? -ne 0; then
      logError "Build failed"
      exit 125
    else
      createImage
    fi
  fi
else
  logInfo "Image no exist. Create a new one"
  createImage
fi

if $? -ne 0; then
  logError "Failed to build docker image. Docker compose starting skipped"
  exit 125
else
  logInfo "Starting docker compose"
  docker compose -f docker-compose.yml build
  docker-compose -f docker-compose.yml up -d
fi
