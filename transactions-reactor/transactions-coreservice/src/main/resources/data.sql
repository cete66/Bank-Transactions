DROP TABLE IF EXISTS TRANSACTION;
DROP TABLE IF EXISTS ACCOUNT;

CREATE TABLE ACCOUNT (
	id INT AUTO_INCREMENT  PRIMARY KEY,
	iban VARCHAR(34) NOT NULL UNIQUE,
	balance DECIMAL DEFAULT 0
);

INSERT INTO ACCOUNT(balance, iban) VALUES (10000, 'ES999999999999999999');


CREATE TABLE TRANSACTION (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  reference VARCHAR(255) UNIQUE,
  account_iban VARCHAR(34) NOT NULL,
  date TIMESTAMP,
  amount DECIMAL NOT NULL,
  fee DECIMAL,
  description VARCHAR(255),
  status VARCHAR(255),
  channel VARCHAR(255)
);

INSERT INTO TRANSACTION(amount, account_iban) VALUES (500, 'ES999999999999999999');

COMMIT;