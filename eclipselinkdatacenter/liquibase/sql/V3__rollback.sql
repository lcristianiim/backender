--liquibase formatted sql

-- rollback cristian:1
DELETE FROM person WHERE first_name = 'Marcel' AND last_name = 'Marco';
