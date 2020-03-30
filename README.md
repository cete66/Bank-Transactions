# Bank-Transactions
Microservice to manage bank transactions


At /Bank-Transactions/Actions/  Maven build and a Docker image are executed for every push to master.

Inside the bank-transactions-curl-tests.zip there are .curl files to basic test the transactions microservice.

After running 'mvn clean install' on the ./transactions-reactor/pom.xml
To launch the microservice, run 'java -jar transactions-web-rs-0.0.1-SNAPSHOT.war' in the path './transactions-reactor/transactions-web-rs/target/' it will be deployed at http://localhost:8080/transactions

There's a private repository in Docker Hub about this repository, could be pulled with 'docker pull robin66/transactions-service:latest'
