--liquibase formatted sql

-- changeset cristian:3
INSERT INTO persons (first_name, last_name) VALUES ('John', 'Doe');

-- changeset cristian:4
INSERT INTO addresses (address, postal_code, person_id) VALUES ('123 Main St', 12345, (SELECT id FROM persons WHERE first_name = 'John' AND last_name = 'Doe'));