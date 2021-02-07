--CREATING DATABASE USER
CREATE USER 'privlib'@'localhost' IDENTIFIED BY 'privlib';
GRANT ALL PRIVILEGES ON * . * TO 'privlib'@'localhost';
FLUSH PRIVILEGES;
SHOW GRANTS FOR 'privlib'@'localhost';
------------------------------------------------------------

--CREATING DATABASE
CREATE TABLE users(
    id_user INT(5) PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL,
    gender VARCHAR(50) NOT NULL,
);

CREATE TABLE books (
    id_book          INT(5) PRIMARY KEY AUTO_INCREMENT,
    title            VARCHAR(50) NOT NULL,
    author           VARCHAR(50) NOT NULL,
    id_genre         INT(5) NOT NULL,
    id_publisher     INT(5) NOT NULL,
    language         VARCHAR(50) NOT NULL,
    description      TEXT NOT NULL,
    publish_date     DATE NOT NULL,
    date_of_return   DATE NULL,
    status           VARCHAR(20) NULL
);

CREATE TABLE user_book (
    id_user_book   INT(5) PRIMARY KEY AUTO_INCREMENT,
    id_user        INT(5) NOT NULL,
    id_book        INT(5) NOT NULL,
    date_added     DATE NOT NULL
);

CREATE TABLE author_book (
    id_author_book   INT(5) PRIMARY KEY AUTO_INCREMENT,
    id_author        INT(5) NOT NULL,
    id_book          INT(5) NOT NULL,
    date_added       DATE NOT NULL
);

CREATE TABLE authors(
    id_user INT(5) PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL,
    gender VARCHAR(50) NOT NULL,
    biography      TEXT NOT NULL
);

CREATE TABLE genres(
    id_user INT(5) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    description      TEXT NOT NULL
);

CREATE TABLE publishers(
    id_user INT(5) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    date_of_foundation     DATE NOT NULL,
    description      TEXT NOT NULL
);
---------------------------------------------------------

ALTER TABLE user_book
    ADD CONSTRAINT id_user_fk FOREIGN KEY ( id_user )
        REFERENCES users ( id_user );

ALTER TABLE user_book
    ADD CONSTRAINT id_book_fk FOREIGN KEY ( id_book )
        REFERENCES books ( id_book );

ALTER TABLE author_book
    ADD CONSTRAINT id_author_fk FOREIGN KEY ( id_author )
        REFERENCES authors ( id_author );

ALTER TABLE author_book
    ADD CONSTRAINT id_book_fk FOREIGN KEY ( id_book )
        REFERENCES books ( id_book );

CREATE UNIQUE INDEX idx_username ON
    users(username);

CREATE UNIQUE INDEX idx_email ON
    users(email);

CREATE INDEX idx_first_last_name ON
    users(first_name, last_name);

CREATE INDEX idx_title_author ON
    books(title, author);

ALTER TABLE books
    ADD CONSTRAINT id_genre_fk FOREIGN KEY ( id_genre )
        REFERENCES genres ( id_genre );

INSERT INTO publishers VALUES(1,"Supernowa",1980-01-01,"only fantasy books");
INSERT INTO publishers VALUES(2,"Albatros",1970-01-01,"godfather for example");
INSERT INTO publishers VALUES(3,"Helion",1990-01-01,"science");
INSERT INTO publishers VALUES(4,"ZYSK I S-KA",2005-01-01,"everything");
INSERT INTO publishers VALUES(5,"carta blanca",2010-01-01,"science");

ALTER TABLE books
    ADD CONSTRAINT id_publisher_fk FOREIGN KEY ( id_publisher )
        REFERENCES publishers ( id_publisher );

ALTER TABLE books
Change author id_author INT(50);

ALTER TABLE books
    ADD CONSTRAINT id_author_fk FOREIGN KEY ( id_author )
        REFERENCES privlib.authors ( id_author );

ALTER TABLE author_book
    ADD CONSTRAINT id_au_author_fk FOREIGN KEY ( id_author )
        REFERENCES authors ( id_author );

ALTER TABLE author_book
    ADD CONSTRAINT id_au_book_fk FOREIGN KEY ( id_book )
        REFERENCES books ( id_book );