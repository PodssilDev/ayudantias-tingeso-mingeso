FROM openjdk:18
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} config-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/config-service-0.0.1-SNAPSHOT.jar"]