--liquibase formatted sql

-- changeset cristian:1
INSERT INTO person (first_name, last_name) VALUES ('John', 'Doe');
INSERT INTO address (address, postal_code, person_id) VALUES ('123 Main St', 12345, (SELECT id FROM person WHERE first_name = 'John' AND last_name = 'Doe'));
