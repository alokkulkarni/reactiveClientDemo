FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/reactiveClientDemo-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
EXPOSE 8082:8082
ENTRYPOINT exec java -jar /app.jar --debug