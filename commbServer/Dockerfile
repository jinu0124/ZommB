FROM openjdk:8-jre-alpine

ARG JAR_FILE=build/libs/commbServer-0.0.1-SNAPSHOT.jar 
COPY ${JAR_FILE} app.jar 
ENTRYPOINT ["java","-jar","/app.jar"]


# COPY . ./commbServer
# WORKDIR /commbServer

# CMD ["./gradlew", "bootRun"]
# CMD ["/usr/bin/java", "-jar", "app.jar"]
