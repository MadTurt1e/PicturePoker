#!/bin/bash
docker compose build
# Manually remove the "desktop" line from this file. We should be able to automate this,
# but this is the amount of bash I will learn today.
gedit  ~/.docker/config.json

# Login to the Azure webserver
az acr login --name luigipoker

# Push docker files (NOTE: we may need to delete existing files to get new updates to reflect, though maybe not)
docker compose push

# Change contexts (this script expects the context to be created already
docker context use luigipokeraci

# Actually run the thing
docker compose up
docker ps

# Run this to kill the server and save money
#docker compose down