# Imagen del contenedor que ejecuta tu código
FROM alpine:3.10
# Archivo del código a ejecutar cuando comienza el contedor del docker (`entrypoint.sh`)
ENTRYPOINT ["/entrypoint.sh"]
WORKDIR /home/runner/work/Bank-Transactions/transactions-reactor
COPY /home/runner/work/Bank-Transactions/transactions-reactor/pom.xml .
RUN mvn package
