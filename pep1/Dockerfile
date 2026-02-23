FROM  eclipse-temurin:25-jre-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} pep1-0.0.1-SNAPSHOT.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/pep1-0.0.1-SNAPSHOT.jar"]
