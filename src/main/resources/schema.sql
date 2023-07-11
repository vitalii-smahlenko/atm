CREATE TABLE atms
(
    id SERIAL PRIMARY KEY
);

CREATE TABLE banknotes
(
    id     SERIAL PRIMARY KEY,
    value  INT,
    atm_id INT,
    FOREIGN KEY (atm_id) REFERENCES atms (id)
);

CREATE TABLE roles
(
    id         SERIAL PRIMARY KEY,
    role_name VARCHAR(50)
);

CREATE TABLE Users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE bank_accounts
(
    id      SERIAL PRIMARY KEY,
    user_id INT,
    balance DECIMAL(19, 2),
    FOREIGN KEY (user_id) REFERENCES Users (id)
);

CREATE TABLE user_roles
(
    user_id INT,
    role_id INT,
    FOREIGN KEY (user_id) REFERENCES Users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE transactions
(
    id                     SERIAL PRIMARY KEY,
    amount                 DECIMAL(19, 2) NOT NULL,
    date_time              TIMESTAMP      NOT NULL,
    source_account_id      INT,
    destination_account_id INT,
    FOREIGN KEY (source_account_id) REFERENCES bank_accounts (id),
    FOREIGN KEY (destination_account_id) REFERENCES bank_accounts (id)
);
