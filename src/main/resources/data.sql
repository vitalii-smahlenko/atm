INSERT INTO atms (id)
VALUES (1);

INSERT INTO banknotes (value, atm_id)
VALUES (100, 1);

INSERT INTO banknotes (value, atm_id)
VALUES (200, 1);

INSERT INTO banknotes (value, atm_id)
VALUES (500, 1);

INSERT INTO roles (role_name)
VALUES ('USER');

INSERT INTO Users (username, password)
VALUES ('user', 'user');

INSERT INTO user_roles (user_id, role_id)
SELECT users.id, roles.id
FROM users, roles
WHERE users.username = 'user' AND roles.role_name = 'USER';