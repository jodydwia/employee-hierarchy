FROM openjdk:17-jdk-slim
EXPOSE 9090
ARG JAR_FILE=target/employee-demo-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]