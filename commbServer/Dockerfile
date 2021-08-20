# FROM openjdk:8-jre-alpine

# ARG JAR_FILE=build/libs/commbServer-0.0.1-SNAPSHOT.jar 
# COPY ${JAR_FILE} app.jar 
# ENTRYPOINT ["java","-jar","/app.jar"]

# WORKDIR /commbServer
# COPY . .


# ENTRYPOINT ["./gradlew", "clean", "build"]
# ENTRYPOINT ["java","-jar","./build/libs/commbServer-0.0.1-SNAPSHOT.jar"]
# CMD ["./gradlew", "bootRun"]
# CMD ["/usr/bin/java", "-jar", "app.jar"]


# FROM adoptopenjdk:8-jdk-hotspot AS builder
# COPY gradlew .
# COPY gradle gradle
# COPY build.gradle .
# COPY settings.gradle .
# COPY src src
# RUN chmod +x ./gradlew
# RUN ./gradlew bootJar

# FROM adoptopenjdk:8-jdk-hotspot
# COPY --from=builder build/libs/*.jar app.jar
# ARG ENVIRONMENT
# ENV SPRING_PROFILES_ACTIVE=${ENVIRONMENT}

# ENTRYPOINT ["java", "-jar", "/app.jar"]


FROM openjdk:8-jdk-alpine as builder
WORKDIR application
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract 

FROM openjdk:8-jdk-alpine
WORKDIR application
ARG DOMAIN
ENV port 8080
ENV spring.profiles.active local
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
RUN echo "$DOMAIN"
ENV DOMAIN=$DOMAIN
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]