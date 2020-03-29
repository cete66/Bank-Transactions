FROM maven:3.5-jdk-8-alpine
WORKDIR ./transactions-reactor
RUN mvn install
FROM openjdk:8-jre-alpine
EXPOSE 8080
CMD ["java -jar ./transactions-war/target/transactions-war-0.0.1-SNAPSHOT.war"]
