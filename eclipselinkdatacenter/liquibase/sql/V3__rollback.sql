--liquibase formatted sql

-- changeset cristian:1 remove address
DELETE FROM persons WHERE first_name = 'Marcel' AND last_name = 'Marco';

-- changeset cristian:2 remove user
DELETE FROM addresses WHERE address = 'Palermo' AND postal_code = 12345 AND person_id = (SELECT id FROM persons WHERE first_name = 'Marcel' AND last_name = 'Marco');
