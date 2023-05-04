FROM openjdk:18
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} eureka-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/eureka-service-0.0.1-SNAPSHOT.jar"]