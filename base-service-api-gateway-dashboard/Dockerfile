FROM openjdk:8-jdk-alpine
COPY ./target/base-service-api-gateway-dashboard-*-SNAPSHOT.jar /app/
WORKDIR /app
ENV PARAMS=""
ENTRYPOINT ["sh","-c","java -jar base-service-api-gateway-dashboard-*-SNAPSHOT.jar $PARAMS"]
