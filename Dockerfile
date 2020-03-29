FROM maven:3.5-jdk-8-alpine
WORKDIR /Bank-Transactions
COPY ./transactions-reactor /Bank-Transactions
RUN mvn install
FROM openjdk:8-jre-alpine
WORKDIR /Bank-Transactions
COPY ./transactions-war/target/transactions-war-0.0.1-SNAPSHOT.war /Bank-Transactions
EXPOSE 8080
CMD ["java -jar transactions-war-0.0.1-SNAPSHOT.war"]
