FROM eclipse-temurin:21.0.8_9-jdk

WORKDIR /app

ARG JAR_FILE

COPY ${JAR_FILE} app.jar

EXPOSE 8080:8080

ENTRYPOINT [ "java", "-jar", "app.jar" ]