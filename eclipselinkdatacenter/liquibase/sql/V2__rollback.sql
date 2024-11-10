--liquibase formatted sql

-- changeset cristian:1 remove address
DELETE FROM persons WHERE first_name = 'John' AND last_name = 'Doe';

-- changeset cristian:2 remove user
DELETE FROM addresses WHERE address = '123 Main St' AND postal_code = 12345 AND person_id = (SELECT id FROM persons WHERE first_name = 'John' AND last_name = 'Doe');