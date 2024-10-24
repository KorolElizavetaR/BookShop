CREATE TABLE location_points
(
  location_id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 10000),
  city varchar (20) NOT NULL,
  address text NOT NULL UNIQUE,
  is_storage bool NOT NULL DEFAULT false
);
SELECT * FROM location_points;

INSERT INTO location_points(city, address, is_storage) VALUES ('Минск', 'ул. проспект Независимости, 25', true);
INSERT INTO location_points(city, address) VALUES 
	('Минск', 'Игуменский тракт, 14'), 
	('Минск', 'пр. Независимости, 92'),
	('Минск', 'пр. Победителей, 3'),
	('Минск', 'ул. В. Хоружей, 17'),
	('Минск', 'ул. Леонида Беды, 46, 92'),
	('Брест', 'б-р Космонавтов, 40'),
	('Гомель', 'пр-т Ленина, 34'),
	('Витебск', 'пр-т Строителей, 15 Г'),
	('Гродно', 'ТРК «TRINITI», пр-т Янки Купалы, 87'),
	('Гродно', 'ТЦ «ЦУМ», ул. Советская, 18');

CREATE TYPE authority AS ENUM ('ROLE_CUSTOMER', 'ROLE_SHOP_ASSISTANT', 'ROLE_ADMIN', 'ROLE_ECONOMIST');

CREATE TABLE person
(
	person_id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	first_name varchar (50) NOT NULL,
	last_name varchar (50) NOT NULL,
	email varchar (100) UNIQUE NOT NULL,
	phone varchar (20) UNIQUE,
	bpassword text NOT NULL,  
	current_location int REFERENCES location_points(location_id)
);

 INSERT INTO person (first_name, last_name, email, phone, bpassword, current_location) VALUES 
    ('Иван', 'Иванов', 'ivan.ivanov@example.com', '+375(29)123-45-67', '$2a$10$k1XzKdCZz.E9RYhvThTyBuROIfACGvCJgKH.eZNeCnG5YmOpIo5.y', 10001), -- Оригинальный пароль: Ivan2024!
    ('Алексей', 'Смирнов', 'alexey.smirnov@example.com', '+375(33)234-56-78', '$2a$10$Q9wFFltzSdhjqfT/QgD2NOHO0gQ5NXuy5BeGZ2y.XVJNLNnHlVYui', 10002), -- Оригинальный пароль: Alex1234@
    ('Мария', 'Кузнецова', 'maria.kuznetsova@example.com', '+375(25)345-67-89', '$2a$10$4Pn3PMexl7xM5LPj9jrnVOj/yjbqOgb/xJdrFif9KeN0np1ALziK.', 10002), -- Оригинальный пароль: Maria4567#
    ('Елена', 'Новикова', 'elena.novikova@example.com', '+375(44)456-78-90', '$2a$10$tM8EkJu7nq/RylZ5fhOQuuDqljEBYhzPKwCHnMCdY.m5VHDIorEmG', 10005), -- Оригинальный пароль: Elena7890!
    ('Дмитрий', 'Ковалев', 'dmitry.kovalev@example.com', '+375(29)567-89-01', '$2a$10$wTj.ZnwrjPZIEGlq58jkL.I9Bd0ddZGPEkRvuhgT5CkWrgkmL9hPS', 10005), -- Оригинальный пароль: Dima2024@
    ('Ольга', 'Сидорова', 'olga.sidorova@example.com', '+375(33)678-90-12', '$2a$10$SGePEFA5uN1mSb8mFBuwle.RTu7yF8MZ8uPU5fuDdCJY1Do5E1v8G', 10005), -- Оригинальный пароль: Olga1234!
    ('Сергей', 'Морозов', 'sergey.morozov@example.com', '+375(25)789-01-23', '$2a$10$YY/T6.aUtTQC9vBxvjNUO.Iv/xTnvk8tB9J5RqT0Zz2lCrEQGuPCq', 10005), -- Оригинальный пароль: Sergey5678!
    ('Анна', 'Петрова', 'anna.petrova@example.com', '+375(44)890-12-34', '$2a$10$X94ERt9I/V4Bgh0RIlsziOvbHtP0MeJ/EULssjtsCr5aFJzWbUv8a', 10005), -- Оригинальный пароль: Anna2024!
    ('Виктор', 'Федоров', 'victor.fedorov@example.com', '+375(29)901-23-45', '$2a$10$0EOA5v1sGHpbhjyxPmTh.eJ0X7mWfdDreCZ9f4X6EQuw0oTflklra', 10005), -- Оригинальный пароль: Victor1234@
    ('Юлия', 'Зайцева', 'yulia.zaitseva@example.com', '+375(33)012-34-56', '$2a$10$G.Xb/PwXAVTD5SCeh8DfKu3n2mHf.HZ9FUBq3PRa/GO.y1ryXjT/q', 10005) -- Оригинальный пароль: Yulia2024!

CREATE TABLE user_authorities
(
	person_id int,
	user_authority authority,
	PRIMARY KEY (person_id, user_authority)
);

INSERT INTO user_authorities (person_id, user_authority) VALUES (1, 'ROLE_CUSTOMER'),
(2, 'ROLE_CUSTOMER'), (3, 'ROLE_CUSTOMER'), (4, 'ROLE_CUSTOMER'),
(5, 'ROLE_CUSTOMER'), (6, 'ROLE_CUSTOMER'), (7, 'ROLE_CUSTOMER'),
(8, 'ROLE_CUSTOMER'), (9, 'ROLE_CUSTOMER'), (10, 'ROLE_CUSTOMER');

BEGIN;

-- INSERT into the 'person' table with RETURNING for capturing person_id (without phone numbers and with current_location = 10004)
WITH inserted_persons AS (
    INSERT INTO person (first_name, last_name, email, bpassword, current_location)
    VALUES 
    ('Андрей', 'Васильев', 'andrey.vasilyev@example.com', '$2a$10$D0X9rLjrxepZg6oNUvAlpehHUJMd67Zux1.lMznFwCBxbvxYORmSa', 10004), -- Оригинальный пароль: Andrey2024!
    ('Светлана', 'Михайлова', 'svetlana.mikhaylova@example.com', '$2a$10$nN8RlHb45OGvGMSixzN9lu2AEMViFZwHHu1M7y1zFE.dGwn2mpya6', 10004), -- Оригинальный пароль: Svetlana1234@
    ('Артем', 'Николаев', 'artem.nikolaev@example.com', '$2a$10$f1Ok8IYX4L6JpjY4v78UgeXoYr1X5AvW8hNllJ67/fBk4EOgU4fDK', 10004), -- Оригинальный пароль: Artem4567#
    ('Оксана', 'Попова', 'oksana.popova@example.com', '$2a$10$H13oIvJ1mGRd1Z2HQnpFS.tanod6CT.r5GJcyQgl9hNiw09DqKzVS', 10004), -- Оригинальный пароль: Oksana7890!
    ('Георгий', 'Соколов', 'georgiy.sokolov@example.com', '$2a$10$c3SvhgZWkT/rTeoL7w/Aue8RtIoM2pjsXMOwKtt/Pm9Hi6q4exJhC', 10004), -- Оригинальный пароль: Georgiy2024@
    ('Екатерина', 'Лебедева', 'ekaterina.lebedeva@example.com', '$2a$10$GvL7ISzy1.pchDck6FAIpeTRu0t/JPrspROEkH/av9M3HVUu7T2va', 10004), -- Оригинальный пароль: Ekaterina1234!
    ('Максим', 'Романов', 'maksim.romanov@example.com', '$2a$10$Z6y2RoHApGjqOiTEV5IpXeoT8NyZAWPUivxW5DWgNEMVfFXYwaZqK', 10004), -- Оригинальный пароль: Maksim5678!
    ('Полина', 'Павлова', 'polina.pavlova@example.com', '$2a$10$pW92WV5NsMHiYm0KuFy.luZXHZAsxs19LNdvUBGjC8GzAijIxEWHu', 10004), -- Оригинальный пароль: Polina2024!
    ('Никита', 'Киселев', 'nikita.kiselev@example.com', '$2a$10$4BPlu1IvMgM1k6TQ4AXXUOifLmpKsR2Cj2f3uhbvE9F3U7.qrTx0G', 10004), -- Оригинальный пароль: Nikita1234@
    ('Татьяна', 'Григорьева', 'tatyana.grigoryeva@example.com', '$2a$10$efpr4SOJoF1.K9yxb6oEQuWgLkck/J6IbuMsoxx6k5ijQoMIxAbP6', 10004) -- Оригинальный пароль: Tatyana2024!
    RETURNING person_id
)

-- INSERT into 'user_authorities' with 'ROLE_CUSTOMER' for each person inserted above
INSERT INTO user_authorities (person_id, user_authority)
SELECT person_id, 'ROLE_CUSTOMER' FROM inserted_persons;

COMMIT;

INSERT INTO person (first_name, last_name, email, bpassword, current_location) VALUES 
    ('Антонина', 'Антюшеня', 'tonyaantush2000@gmail.com', '$2a$12$2FE57DtSZaQvMUAr3LBWCe6lPqO/CZtKxOrx1A5KDzHQFGqYMfd.6', 10004), -- antushenya@ shop_assistant, customer
	('Лиза', 'Король', 'elizavetakorol2005@gmail.com', '$2a$12$E.roMn1yneAdRch/wnEtIOp06B1o/bWqWSSJxQtvnHo9/mDsJHEMC', 10001), -- 321456 admin customer
	('Роман', 'Науменко', 'romannaumenko1990@gmail.com', '$2a$12$DFKiD/M.NDdpTyBFM9hiye23ysLYPaj0QI4jVdZElxuQ8NGuUjhfO', 10003); -- userpassword economist 

INSERT INTO user_authorities (person_id, user_authority) VALUES 
	(21, 'ROLE_CUSTOMER'), (21, 'ROLE_SHOP_ASSISTANT'),
	(22, 'ROLE_CUSTOMER'), (22, 'ROLE_ADMIN'),
	(23, 'ROLE_CUSTOMER'), (23, 'ROLE_ECONOMIST');
	
CREATE TABLE book
(
	isbn varchar(13) PRIMARY KEY CHECK (LENGTH(isbn) = 13),
	title varchar(255) NOT NULL,
	author varchar(127) NOT NULL,
	publisher varchar(127) NOT NULL,
	description text,
	cover_url text NOT NULL
);
DROP TABLE book;

INSERT INTO book VALUES
	('9781909115026', 'Война и Мир', 'Лев Толстой', 'Planet', '«Война и мир» считается одним из самых значительных произведений мировой классической литературы, а также крупнейшим литературным достижением Толстого, наряду с его другим романом «Анна Каренина». Толстой писал роман на протяжении 6 лет, с 1863 по 1869 годы. По историческим сведениям он вручную переписал его примерно 7 раз, а отдельные эпизоды — более 26 раз. Русский критик Н. Н. Страхов писал: “В таких великих произведениях, как «Война и мир», всего яснее открывается истинная сущность и важность искусства. Поэтому «Война и мир» есть превосходный пробный камень всякого критического и эстетического понимания, а вместе с тем и камень преткновения для всякой глупости и всякого нахальства. Легко понять, что не «Войну и мир» будут ценить по вашим словам и мнениям, а вас будут судить по тому, что вы скажете о «Войне и мире».”', 'https://planetabook.ru/upload/iblock/819/rfsudugyflep16nynucw50axzqlh3dxu.jpg'),
	('9788826070643', 'Бесы', 'Федор Достоевский', 'Shelkoper.com', '«Бе́сы» — шестой роман Фёдора Михайловича Достоевского, изданный в 1871—1872 годах. Один из наиболее политизированных романов Достоевского был написан им под впечатлением от ростков террористического и радикального движений в среде русских интеллигентов, разночинцев и прочих.', 'https://cdn.kobo.com/book-images/8dffc724-bc7a-482c-bbf7-e30fc3ab85da/600/600/False/image.jpg'),
	('9785040400430', 'Зов Ктулху (сборник)', 'Говард Лавкрафт', 'ACT', 'Дагон, Ктулху, Йог-Сотот и многие другие темные божества, придуманные Говардом Лавкрафтом в 1920-е годы, приобрели впоследствии такую популярность, что сотни творцов фантастики, включая Нила Геймана и Стивена Кинга, до сих пор продолжают расширять его мифологию. Каждое монструозное божество в лавкрафтианском пантеоне олицетворяет собой одну из бесчисленных граней хаоса. Таящиеся в глубинах океана или пребывающие в глубине непроходимых лесов, спящие в египетских пирамидах или замурованные в горных пещерах, явившиеся на нашу планету со звезд или из бездны неисчислимых веков, они неизменно враждебны человечеству и неподвластны разуму. И единственное, что остается человеку – это всячески избегать столкновения с этими таинственными существами и держаться настороже...', 'https://cdn.ast.ru/v2/ASE000000000836337/COVER/cover1__w340.jpg');

CREATE TABLE book_product