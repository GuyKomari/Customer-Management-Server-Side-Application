FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ADD target/server.jar server.jar 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","server.jar"]