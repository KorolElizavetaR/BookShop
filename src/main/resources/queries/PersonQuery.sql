CREATE TABLE person
(
	person_id int REFERENCES users(user_id) ON DELETE CASCADE PRIMARY KEY,
	first_name varchar (40) NOT NULL,
	last_name varchar (40) NOT NULL,
	dob date NOT NULL,
	email varchar (70) UNIQUE,
	phone varchar (20) UNIQUE,
	created_at timestamp NOT NULL default now(),
	current_shop_location int REFERENCES shop_points(loc_id)
)
DROP TABLE person;
SELECT * FROM person;

INSERT INTO person(person_id, first_name, last_name, dob, current_shop_location) VALUES
	((SELECT user_id FROM users WHERE username = 'username'), 'Пользователь', 'Пользоватич', '14.10.2010', 10001),
	((SELECT user_id FROM users WHERE username = 'alesyaivanova2001'), 'Алеся', 'Иванова', '01.12.2003', 10001),
	((SELECT user_id FROM users WHERE username = 'anonimus_book_enjoyer'), 'Анонимный', 'Книголюб', '04.04.2006', 10004);
	
INSERT INTO person(person_id, first_name, last_name, dob) VALUES
	((SELECT user_id FROM users WHERE username = 'admin'), 'Елизавета', 'Король', '05.01.2005'),
	((SELECT user_id FROM users WHERE username = 'VladimirVP'), 'Владимир', 'Пастернак', '12.11.1989');