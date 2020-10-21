CREATE TABLE IF NOT EXISTS lists
(
    id integer PRIMARY KEY,
    title VARCHAR(250) NOT NULL,
    createdate DATE NOT NULL,
    changedate DATE
);