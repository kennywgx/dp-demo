#!/bin/bash

docker run \
  --name $CONTAINER_NAME \
  --restart=on-failure:5 \
  --privileged \
  -e SW_SERVICE_ADDR=127.0.0.1:11800 \
  -p $PORT:8080 \
  -v $PWD/logs:/home/logs \
  -d $IMG_NAME \
    --spring.profiles.active=prod
