CREATE TABLE shop_points
(
  loc_id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 10000),
  city varchar (20) NOT NULL,
  address text NOT NULL
)

INSERT INTO shop_points(city, address) VALUES
	('Минск', 'Игуменский тракт, 14'),
	('Минск', 'Логойский тракт, 37'),
	('Минск', 'пр-т Рокоссовского, 77 В'),
	('Барановичи', 'ул. Ленина, 4'),
	('Бобруйск', 'ул. Октябрьская, 151B')
	
SELECT * FROM shop_points