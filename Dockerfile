#FROM openjdk:8-jdk-alpine
FROM openjdk:8
MAINTAINER paulo.machado
VOLUME /tmp
ARG JAR_FILE=target/payment-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} payment-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/payment-service-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080