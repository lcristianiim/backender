--liquibase formatted sql

-- changeset cristian:1
INSERT INTO person (first_name, last_name) VALUES ('Marcel', 'Marco');
INSERT INTO address (address, postal_code, person_id) VALUES ('str Palermo', 12345, (SELECT id FROM person WHERE first_name = 'Marcel' AND last_name = 'Marco'));