--liquibase formatted sql

-- changeset cristian:1
INSERT INTO persons (first_name, last_name) VALUES ('Marcel', 'Marco');

-- changeset cristian:2
INSERT INTO addresses (address, postal_code, person_id) VALUES ('Palermo', 12345, (SELECT id FROM persons WHERE first_name = 'Marcel' AND last_name = 'Marco'));
