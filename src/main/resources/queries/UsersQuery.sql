DROP TABLE users;

TRUNCATE TABLE users CASCADE;

CREATE TABLE users
(
	user_id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	username varchar (40) NOT NULL UNIQUE,
	bpassword text NOT NULL
)

SELECT * FROM users;

INSERT INTO users(username, bpassword) VALUES
	('username', '$2a$12$iWlxElHCduMfC1OyuuV3ZOQJigsfXZRKg8FdXZdLGd9khp7cr0E4q'), -- password
	('admin', '$2a$12$4vM1i/eeGuaZ89.f7b6Q0u.9dRFoeCtbRMKPbSd/23.iVAMZQVDZu'), -- admin
	('alesyaivanova2001', '$2a$12$5RN/BQo/PWlGiajAMRqScu0ShXxBFByFu.6610d/Mj5nLlLhBxU1W'), -- Alesya2001
	('VladimirVP', '$2a$12$mLbgrbg7dtAxVCS9UASrreO.s3JNj0m3cpHwt.tT/3Jr.4JGtdxaq'), -- PostavkaKnig
	('anonimus_book_enjoyer', '$2a$12$fag1Vq19NHSR0yG25ojNIOdQEKfH6mgexev9GVcY0ojlqV7fC8mle') -- ILoveBooks