CREATE TABLE book
(
	isbn varchar(13) PRIMARY KEY CHECK (LEN(isbn) = 13),
	title text NOT NULL,
	author text NOT NULL,
	publisher text NOT NULL,
	language varchar(3) NOT NULL,
	description text,
	cover_i int NOT NULL,
)