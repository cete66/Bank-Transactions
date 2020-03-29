FROM alpine/git
WORKDIR /Bank-Transactions
RUN git clone https://github.com/cete66/Bank-Transactions.git (1)
FROM maven:3.5-jdk-8-alpine
WORKDIR /Bank-Transactions
COPY --from=0 /Bank-Transactions/transactions-reactor /Bank-Transactions (2)
RUN mvn install (3)
FROM openjdk:8-jre-alpine
WORKDIR /Bank-Transactions
COPY --from=1 /Bank-Transactions/transactions-reactor/transactions-war/target/transactions-war-0.0.1-SNAPSHOT.war /Bank-Transactions (4)
CMD ["java -jar transactions-war-0.0.1-SNAPSHOT.war"] (5)
