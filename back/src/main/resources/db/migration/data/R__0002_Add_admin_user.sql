--date: 2023-12-30
--author: tadanoluka

INSERT INTO users(username, firstname, lastname, role_id)
VALUES ('apiAdmin', 'admin', 'admin', (SELECT id FROM roles WHERE name = 'admin'));