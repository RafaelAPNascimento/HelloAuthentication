FROM maven:3.6.3-openjdk-11-slim AS Builder
LABEL key="https://finalexception.blogspot.com"

COPY ./pom.xml /app/pom.xml
COPY ./src /app/src
WORKDIR /app
RUN mvn clean package

FROM jboss/wildfly
EXPOSE 8080
COPY --from=Builder /app/target/hello-authentication.war /opt/jboss/wildfly/standalone/deployments/