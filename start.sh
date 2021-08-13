# /bin/bash
# if you want run -> bash start.sh
# docker rmi $(docker images -f "dangling=true" -q) -f
cd commbServer && ./gradlew clean build && cd ..
# docker-compose down --volume
# docker-compose build
# docker-compose up

docker-compose pull

docker-compose up --force-recreate --build -d

docker rmi $(docker images -f "dangling=true" -q) -f
