# /bin/bash
# if you want run -> bash start.sh
# docker rmi $(docker images -f "dangling=true" -q) -f
# cd commbServer && ./gradlew clean build && cd ..
# docker-compose down --volume
# docker-compose build
# docker-compose up

cd commbServer && ./gradlew bootJar && cd ..
docker-compose pull

COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose up --build -d

docker rmi $(docker images -f "dangling=true" -q) -f
