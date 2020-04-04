FROM maven:3.5-jdk-8-alpine
WORKDIR /Bank-Transactions
COPY ./transactions-reactor /Bank-Transactions
RUN mvn install
FROM openjdk:8-jre-alpine
EXPOSE 8080
CMD ["java -jar ./transactions-web-rs/target/transactions-web-rs-0.0.1-SNAPSHOT.war"]
