--liquibase formatted sql

-- changeset cristian:1
CREATE TABLE persons (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

-- changeset cristian:2
CREATE TABLE addresses (
    id SERIAL PRIMARY KEY,
    address VARCHAR(255) NOT NULL,
    postal_code INT NOT NULL,
    person_id INT,
    FOREIGN KEY (person_id) REFERENCES persons(id) ON DELETE CASCADE
);