--liquibase formatted sql

-- changeset cristian:1 remove address
DELETE FROM address WHERE person_id = (SELECT id FROM person WHERE first_name = 'John' AND last_name = 'Doe');

-- changeset cristian:2 remove user
DELETE FROM person WHERE first_name = 'John' AND last_name = 'Doe';