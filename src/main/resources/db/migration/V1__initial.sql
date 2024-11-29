CREATE TYPE authority AS ENUM ('ROLE_CUSTOMER', 'ROLE_SHOP_ASSISTANT', 'ROLE_ADMIN', 'ROLE_ECONOMIST');
CREATE TYPE order_status AS ENUM ('CLOSED', 'CANCELED', 'ARRIVED', 'PENDING_ARRIVAL' , 'SHOPPING_BIN');

CREATE TABLE location_points
(
  location_id char(5) PRIMARY KEY CHECK (location_id ~ '\d{5}'),
  city varchar (20) NOT NULL,
  address text NOT NULL UNIQUE,
  is_storage bool NOT NULL DEFAULT false
);

ALTER TABLE location_points ADD CONSTRAINT city_format_check CHECK (city ~ '[А-ЯЁ][-А-яЁё]+');

INSERT INTO location_points VALUES ('00000','Минск', 'ул. проспект Независимости, 25', true);
INSERT INTO location_points(location_id, city, address) VALUES 
	('10000','Минск', 'Игуменский тракт, 14'), 
	('10001', 'Минск', 'пр. Независимости, 92'),
	('10002', 'Минск', 'пр. Победителей, 3'),
	('10003', 'Минск', 'ул. В. Хоружей, 17'),
	('10004', 'Минск', 'ул. Леонида Беды, 46, 92'),
	('20000', 'Брест', 'б-р Космонавтов, 40'),
	('30000', 'Гомель', 'пр-т Ленина, 34'),
	('40000', 'Витебск', 'пр-т Строителей, 15 Г'),
	('50000','Гродно', 'ТРК «TRINITI», пр-т Янки Купалы, 87'),
	('50001','Гродно', 'ТЦ «ЦУМ», ул. Советская, 18');

CREATE TABLE person
(
	person_id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	first_name varchar (50) NOT NULL,
	last_name varchar (50) NOT NULL,
	email varchar (100) UNIQUE NOT NULL,
	phone varchar (20) UNIQUE,
	bpassword text NOT NULL,  
	current_location char(5) REFERENCES location_points(location_id)
);
ALTER TABLE person ALTER COLUMN last_name DROP NOT NULL;
 
CREATE TABLE user_authorities
(
	person_id int REFERENCES person(person_id),
	user_authority authority,
	PRIMARY KEY (person_id, user_authority)
);

WITH inserted_persons AS (
    INSERT INTO person (first_name, last_name, email, phone, bpassword, current_location)
    VALUES 
    ('Андрей', 'Васильев', 'andrey.vasilyev@example.com', '+375(44)123-45-67', '$2a$10$D0X9rLjrxepZg6oNUvAlpehHUJMd67Zux1.lMznFwCBxbvxYORmSa', 10000), -- Original password: Andrey2024!
    ('Светлана', 'Михайлова', 'svetlana.mikhaylova@example.com', '+375(29)234-56-78', '$2a$10$nN8RlHb45OGvGMSixzN9lu2AEMViFZwHHu1M7y1zFE.dGwn2mpya6', 10004), -- Original password: Svetlana1234@
    ('Артем', 'Николаев', 'artem.nikolaev@example.com', '+375(29)345-67-89', '$2a$10$f1Ok8IYX4L6JpjY4v78UgeXoYr1X5AvW8hNllJ67/fBk4EOgU4fDK', 10004), -- Original password: Artem4567#
    ('Оксана', 'Попова', 'oksana.popova@example.com', '+375(29)456-78-90', '$2a$10$H13oIvJ1mGRd1Z2HQnpFS.tanod6CT.r5GJcyQgl9hNiw09DqKzVS', 10004), -- Original password: Oksana7890!
    ('Георгий', 'Соколов', 'georgiy.sokolov@example.com', '+375(44)567-89-01', '$2a$10$c3SvhgZWkT/rTeoL7w/Aue8RtIoM2pjsXMOwKtt/Pm9Hi6q4exJhC', 50000), -- Original password: Georgiy2024@
    ('Екатерина', 'Лебедева', 'ekaterina.lebedeva@example.com', '+375(29)678-90-12', '$2a$10$GvL7ISzy1.pchDck6FAIpeTRu0t/JPrspROEkH/av9M3HVUu7T2va', 10004), -- Original password: Ekaterina1234!
    ('Максим', 'Романов', 'maksim.romanov@example.com', '+375(29)789-01-23', '$2a$10$Z6y2RoHApGjqOiTEV5IpXeoT8NyZAWPUivxW5DWgNEMVfFXYwaZqK', 10004), -- Original password: Maksim5678!
    ('Полина', 'Павлова', 'polina.pavlova@example.com', '+375(29)890-12-34', '$2a$10$pW92WV5NsMHiYm0KuFy.luZXHZAsxs19LNdvUBGjC8GzAijIxEWHu', 10000), -- Original password: Polina2024!
    ('Никита', 'Киселев', 'nikita.kiselev@example.com', '+375(44)901-23-45', '$2a$10$4BPlu1IvMgM1k6TQ4AXXUOifLmpKsR2Cj2f3uhbvE9F3U7.qrTx0G', 10004), -- Original password: Nikita1234@
    ('Татьяна', 'Григорьева', 'tatyana.grigoryeva@example.com', '+375(29)012-34-56', '$2a$10$efpr4SOJoF1.K9yxb6oEQuWgLkck/J6IbuMsoxx6k5ijQoMIxAbP6', 10004), -- Original password: Tatyana2024!
    ('Иван', 'Иванов', 'ivan.ivanov@example.com', '+375(29)123-45-67', '$2a$10$k1XzKdCZz.E9RYhvThTyBuROIfACGvCJgKH.eZNeCnG5YmOpIo5.y', 10001), -- Original password: Ivan2024!
    ('Алексей', 'Смирнов', 'alexey.smirnov@example.com', '+375(33)234-56-78', '$2a$10$Q9wFFltzSdhjqfT/QgD2NOHO0gQ5NXuy5BeGZ2y.XVJNLNnHlVYui', 10002), -- Original password: Alex1234@
    ('Мария', 'Кузнецова', 'maria.kuznetsova@example.com', '+375(25)345-67-89', '$2a$10$4Pn3PMexl7xM5LPj9jrnVOj/yjbqOgb/xJdrFif9KeN0np1ALziK.', 10002), -- Original password: Maria4567#
    ('Елена', 'Новикова', 'elena.novikova@example.com', '+375(44)456-78-90', '$2a$10$tM8EkJu7nq/RylZ5fhOQuuDqljEBYhzPKwCHnMCdY.m5VHDIorEmG', 20000), -- Original password: Elena7890!
    ('Дмитрий', 'Ковалев', 'dmitry.kovalev@example.com', '+375(29)567-89-01', '$2a$10$wTj.ZnwrjPZIEGlq58jkL.I9Bd0ddZGPEkRvuhgT5CkWrgkmL9hPS', 10004), -- Original password: Dima2024@
    ('Ольга', 'Сидорова', 'olga.sidorova@example.com', '+375(33)678-90-12', '$2a$10$SGePEFA5uN1mSb8mFBuwle.RTu7yF8MZ8PU5fuDdCJY1Do5E1v8G', 10004), -- Original password: Olga1234!
    ('Сергей', 'Морозов', 'sergey.morozov@example.com', '+375(25)789-01-23', '$2a$10$YY/T6.aUtTQC9vBxvjNUO.Iv/xTnvk8tB9J5RqT0Zz2lCrEQGuPCq', 10004), -- Original password: Sergey5678!
    ('Анна', 'Петрова', 'anna.petrova@example.com', '+375(44)890-12-34', '$2a$10$X94ERt9I/V4Bgh0RIlsziOvbHtP0MeJ/EULssjtsCr5aFJzWbUv8a', 50001), -- Original password: Anna2024!
    ('Виктор', 'Федоров', 'victor.fedorov@example.com', '+375(29)901-23-45', '$2a$10$0EOA5v1sGHpbhjyxPmTh.eJ0X7mWfdDreCZ9f4X6EQuw0oTflklra', 30000), -- Original password: Victor1234@
    ('Юлия', 'Зайцева', 'yulia.zaitseva@example.com', '+375(33)012-34-56', '$2a$10$G.Xb/PwXAVTD5SCeh8DfKu3n2mHf.HZ9FUBq3PRa/GO.y1ryXjT/q', 50001) -- Original password: Tatyana2024!
    RETURNING person_id
)

INSERT INTO user_authorities (person_id, user_authority)
SELECT person_id, 'ROLE_CUSTOMER' FROM inserted_persons;

INSERT INTO person (first_name, last_name, email, bpassword, current_location) VALUES 
    ('Антонина', 'Антюшеня', 'tonyaantush2000@gmail.com', '$2a$12$2FE57DtSZaQvMUAr3LBWCe6lPqO/CZtKxOrx1A5KDzHQFGqYMfd.6', 10004), -- antushenya@ shop_assistant customer
	('Лиза', 'Король', 'elizavetakorol2005@gmail.com', '$2a$12$E.roMn1yneAdRch/wnEtIOp06B1o/bWqWSSJxQtvnHo9/mDsJHEMC', 10001), -- 321456 admin customer
	('Роман', 'Науменко', 'romannaumenko1990@gmail.com', '$2a$12$DFKiD/M.NDdpTyBFM9hiye23ysLYPaj0QI4jVdZElxuQ8NGuUjhfO', 10003); -- userpassword economist 

INSERT INTO user_authorities (person_id, user_authority) VALUES 
	(21, 'ROLE_CUSTOMER'), (21, 'ROLE_SHOP_ASSISTANT'),
	(22, 'ROLE_CUSTOMER'), (22, 'ROLE_ADMIN'),
	(23, 'ROLE_CUSTOMER'), (23, 'ROLE_ECONOMIST');
	
CREATE TABLE book
(
	isbn char(13) PRIMARY KEY CHECK (isbn ~ '\d{13}'),
	title varchar(255) NOT NULL,
	author varchar(127) NOT NULL,
	publisher varchar(127) NOT NULL,
	description text,
	cover_url text -- add def. value of no img available
);

INSERT INTO book VALUES
	('9781909115026', 'Война и Мир', 'Лев Толстой', 'Planet', '«Война и мир» считается одним из самых значительных произведений мировой классической литературы, а также крупнейшим литературным достижением Толстого, наряду с его другим романом «Анна Каренина». Толстой писал роман на протяжении 6 лет, с 1863 по 1869 годы. По историческим сведениям он вручную переписал его примерно 7 раз, а отдельные эпизоды — более 26 раз. Русский критик Н. Н. Страхов писал: “В таких великих произведениях, как «Война и мир», всего яснее открывается истинная сущность и важность искусства. Поэтому «Война и мир» есть превосходный пробный камень всякого критического и эстетического понимания, а вместе с тем и камень преткновения для всякой глупости и всякого нахальства. Легко понять, что не «Войну и мир» будут ценить по вашим словам и мнениям, а вас будут судить по тому, что вы скажете о «Войне и мире».”', 'https://planetabook.ru/upload/iblock/819/rfsudugyflep16nynucw50axzqlh3dxu.jpg'),
	('9788826070643', 'Бесы', 'Федор Достоевский', 'Shelkoper.com', '«Бе́сы» — шестой роман Фёдора Михайловича Достоевского, изданный в 1871—1872 годах. Один из наиболее политизированных романов Достоевского был написан им под впечатлением от ростков террористического и радикального движений в среде русских интеллигентов, разночинцев и прочих.', 'https://cdn.kobo.com/book-images/8dffc724-bc7a-482c-bbf7-e30fc3ab85da/600/600/False/image.jpg'),
	('9785040400430', 'Зов Ктулху (сборник)', 'Говард Лавкрафт', 'ACT', 'Дагон, Ктулху, Йог-Сотот и многие другие темные божества, придуманные Говардом Лавкрафтом в 1920-е годы, приобрели впоследствии такую популярность, что сотни творцов фантастики, включая Нила Геймана и Стивена Кинга, до сих пор продолжают расширять его мифологию. Каждое монструозное божество в лавкрафтианском пантеоне олицетворяет собой одну из бесчисленных граней хаоса. Таящиеся в глубинах океана или пребывающие в глубине непроходимых лесов, спящие в египетских пирамидах или замурованные в горных пещерах, явившиеся на нашу планету со звезд или из бездны неисчислимых веков, они неизменно враждебны человечеству и неподвластны разуму. И единственное, что остается человеку – это всячески избегать столкновения с этими таинственными существами и держаться настороже...', 'https://cdn.ast.ru/v2/ASE000000000836337/COVER/cover1__w340.jpg'),
	('9780140449136', 'Преступление и наказание', 'Фёдор Достоевский', 'Penguin Classics', '«Преступление и наказание» — психологическая драма, исследующая темы вины, морали и искупления.', 'https://imo10.labirint.ru/books/863342/cover.jpg/242-0'),
    ('9780061120084', 'Убить пересмешника', 'Харпер Ли', 'Harper Perennial', 'Роман о расовой несправедливости на американском Юге, рассказанный с точки зрения маленькой Скаут Финч.', 'https://avatars.mds.yandex.net/get-kinopoisk-image/4774061/46b80991-7b6f-484b-b6dd-8cd2cd6ccbe9/220x330'),
    ('9780192833983', 'Гордость и предубеждение', 'Джейн Остин', 'Oxford University Press', 'Классический роман о любви, который также критикует британскую классовую систему.', 'https://cdn.azbooka.ru/cv/w1100/4b7f1f95-3911-44cf-8f5f-13886c29bea8.jpg'),
    ('9780140449181', 'Братья Карамазовы', 'Фёдор Достоевский', 'Азбука', 'Интенсивное исследование веры, сомнений и человеческой природы, сосредоточенное на семье Карамазовых.', 'https://cdn.azbooka.ru/cv/w1100/a6d50e17-c422-4c07-b73d-3b9e722fa1bb.jpg'),
    ('9780307277671', 'Дорога', 'Кормак Маккарти', 'Азбука', 'Постапокалиптический роман о выживании отца и сына в разрушенном мире.', 'https://cdn.azbooka.ru/cv/w1100/67a9f5ef-7aea-46bb-bfc1-6b2fc4d7b25c.jpg'),
    ('9780743273565', 'Великий Гэтсби', 'Фрэнсис Скотт Фицджеральд', 'Азбука', 'Трагическая история любви в эпоху джаза, исследующая темы богатства, власти и американской мечты.', 'https://cdn.azbooka.ru/cv/w1100/5671812c-06f3-4fc7-b2f9-3dab6514bd8b.jpg'),
    ('9780451524935', '1984', 'Джордж Оруэлл', 'Азбука', 'Антиутопия, критикующая тоталитаризм и экстремальные политические идеологии.', 'https://cdn.azbooka.ru/cv/w1100/08fe16b6-f3dc-4a62-a63a-0ac471bf0e9a.jpg'),
    ('9780316769488', 'Над пропастью во ржи', 'Джером Дэвид Сэлинджер', 'Эксмо', 'Роман о взрослении, рассказывающий о Холдена Колфилде, трудном подростке в Нью-Йорке.', 'https://cdn.eksmo.ru/v2/ITD000000000936146/COVER/cover1__w820.jpg'),
    ('9780140449266', 'Анна Каренина', 'Лев Толстой', 'ACT', 'Сложное исследование любви, брака и общества в России XIX века.', 'https://cdn1.ozone.ru/s3/multimedia-5/6009081221.jpg'),
    ('9780553213119', 'Отверженные', 'Виктор Гюго', 'Азбука', 'Монументальное произведение о справедливости, любви и искуплении в послереволюционной Франции.', 'https://cdn.azbooka.ru/cv/w1100/8e4f70bd-f412-4f3c-a9cf-b1755601fb97.jpg')

CREATE TABLE book_product (
    isbn CHAR(13) PRIMARY KEY REFERENCES book(isbn),
    price NUMERIC(10, 2) NOT NULL,
    discount DECIMAL(5, 4) DEFAULT 0
);

INSERT INTO book_product (isbn, price) VALUES
    ('9781909115026', 15.99), ('9788826070643', 12.49), ('9785040400430', 9.99),
    ('9780140449136', 14.99),    ('9780061120084', 10.99),    ('9780192833983', 8.49),
    ('9780140449181', 16.99),    ('9780307277671', 11.49),    ('9780743273565', 13.79),
    ('9780451524935', 7.99),    ('9780316769488', 6.49),    ('9780140449266', 17.89),
    ('9780553213119', 18.00);

CREATE TABLE stock (
    location_id CHAR(5) NOT NULL REFERENCES location_points(location_id),
    isbn CHAR(13) NOT NULL REFERENCES book(isbn),
    quantity SMALLINT NOT NULL,
    PRIMARY KEY (location_id, isbn)
);

ALTER TABLE stock DROP CONSTRAINT stock_isbn_fkey,   -- Assuming the original foreign key constraint is named stock_isbn_fkey
    ADD CONSTRAINT stock_isbn_fkey FOREIGN KEY (isbn) REFERENCES book_product(isbn);

INSERT INTO stock (location_id, isbn, quantity) VALUES 
	-- Location 00000 - stock
	('00000', '9781909115026', 3000),
    ('00000', '9788826070643', 2500),
    ('00000', '9785040400430', 4000),
    ('00000', '9780140449136', 3500),
    ('00000', '9780061120084', 2800),
    ('00000', '9780192833983', 3100),
    ('00000', '9780140449181', 2700),
    ('00000', '9780307277671', 4500),
    ('00000', '9780743273565', 2900),
    ('00000', '9780451524935', 5000),
    ('00000', '9780316769488', 2200),
    ('00000', '9780140449266', 3800),
    ('00000', '9780553213119', 3300),

    -- Location 10000
    ('10000', '9781909115026', 300),
    ('10000', '9788826070643', 250),
    ('10000', '9785040400430', 450),
    ('10000', '9780140449136', 320),
    ('10000', '9780061120084', 280),
    
    -- Location 10001
    ('10001', '9780192833983', 150),
    ('10001', '9780140449181', 200),
    ('10001', '9780307277671', 120),
    ('10001', '9780743273565', 250),
    
    -- Location 10002
    ('10002', '9780451524935', 180),
    ('10002', '9780316769488', 300),
    ('10002', '9780140449266', 130),
    ('10002', '9780553213119', 170),
    
    -- Location 10003
    ('10003', '9781909115026', 350),
    ('10003', '9788826070643', 200),
    ('10003', '9785040400430', 400),
    
    -- Location 10004
    ('10004', '9780140449181', 300),
    ('10004', '9780307277671', 150),
    ('10004', '9780451524935', 400),
    
    -- Location 20000
    ('20000', '9780316769488', 200),
    ('20000', '9780140449266', 130),
    ('20000', '9780553213119', 220),
    
    -- Location 30000
    ('30000', '9780192833983', 190),
    ('30000', '9780140449181', 120),
    ('30000', '9780743273565', 210),
    
    -- Location 40000
    ('40000', '9780451524935', 250),
    ('40000', '9780316769488', 300),
    ('40000', '9780553213119', 140),
    
    -- Location 50000
    ('50000', '9780140449136', 150),
    ('50000', '9780307277671', 220),
    ('50000', '9780061120084', 180),
    
    -- Location 50001
    ('50001', '9780743273565', 300),
    ('50001', '9780451524935', 150),
    ('50001', '9780316769488', 120),
    ('50001', '9781909115026', 250);

CREATE TABLE orders (
    order_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    customer_id INT NOT NULL REFERENCES person(person_id),
    location_id CHAR(5) REFERENCES location_points(location_id) NOT NULL,
    createdAt TIMESTAMP DEFAULT NOW(),
    status order_status NOT NULL,
    closedAt TIMESTAMP,
    shop_assistant_id INT REFERENCES person(person_id),
	 quantity INT NOT NULL CHECK (quantity > 0),
	isbn char(13) REFERENCES book_product(isbn)
);