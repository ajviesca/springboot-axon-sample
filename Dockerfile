FROM adoptopenjdk/openjdk11:alpine-slim

ARG JAR_FILE
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-XX:+UnlockExperimentalVMOptions", "-jar","/app.jar"]
