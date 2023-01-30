FROM openjdk:17-jdk-alpine

ARG JAR_FILE=target/bite-*.jar

COPY ${JAR_FILE} /usr/app/app.jar

WORKDIR /usr/app

ENTRYPOINT ["java","-jar","/usr/app/app.jar"]
