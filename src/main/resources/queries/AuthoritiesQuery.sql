-- Позднее поменяется из-за springsecurity

CREATE TYPE role AS ENUM ('ROLE_CUSTOMER', 'ROLE_SHOP_ASSISTANT', 'ROLE_ADMIN', 'ROLE_SUPPLIER');

DROP TABLE authorities;

CREATE TABLE authorities
(
	user_id int REFERENCES users(user_id) ON DELETE CASCADE,
	authority role NOT NULL,
	PRIMARY KEY (user_id, authority)
)
SELECT * FROM authorities;

INSERT INTO authorities VALUES
	((SELECT user_id FROM users WHERE username = 'username'), 'ROLE_CUSTOMER'),
	((SELECT user_id FROM users WHERE username = 'admin'), 'ROLE_ADMIN'),
	((SELECT user_id FROM users WHERE username = 'alesyaivanova2001'), 'ROLE_SHOP_ASSISTANT'),
	((SELECT user_id FROM users WHERE username = 'VladimirVP'), 'ROLE_SUPPLIER'),
	((SELECT user_id FROM users WHERE username = 'VladimirVP'), 'ROLE_CUSTOMER'),
	((SELECT user_id FROM users WHERE username = 'anonimus_book_enjoyer'), 'ROLE_CUSTOMER')