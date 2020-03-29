FROM maven:3.5-jdk-8-alpine
WORKDIR /Bank-Transactions
COPY --from=0 /Bank-Transactions/transactions-reactor /Bank-Transactions
RUN mvn install
FROM openjdk:8-jre-alpine
WORKDIR /Bank-Transactions
COPY --from=1 /Bank-Transactions/transactions-reactor/transactions-war/target/transactions-war-0.0.1-SNAPSHOT.war /Bank-Transactions
CMD ["java -jar transactions-war-0.0.1-SNAPSHOT.war"]
