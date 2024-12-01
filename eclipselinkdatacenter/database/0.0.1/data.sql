-- Adminer 4.8.1 PostgreSQL 17.0 (Debian 17.0-1.pgdg120+1) dump

\connect "pgmodeler";

INSERT INTO "addresses" ("id", "street", "postal_code") VALUES
(1,	'1,Cluj,12345,2                                    ',	'12345                                             '),
(2,	'1,Cluj,12345,2                                    ',	'12345                                             '),
(3,	'1,Cluj,12345,2                                    ',	'12345                                             '),
(4,	'1,Cluj,12345,2                                    ',	'12345                                             '),
(5,	'1,Cluj,12345,2                                    ',	'12345                                             '),
(6,	'1,Cluj,12345,2                                    ',	'12345                                             '),
(7,	'1,Cluj,12345,2                                    ',	'12345                                             '),
(8,	'1,Cluj,12345,2                                    ',	'12345                                             ');

INSERT INTO "many_persons_has_many_addresses" ("id_persons", "id_addresses") VALUES
(1,	1),
(2,	2),
(3,	3),
(4,	4),
(5,	5),
(6,	6),
(8,	8);

INSERT INTO "persons" ("id", "first_name", "last_name") VALUES
(1,	'Honda                                             ',	'Motora                                            '),
(2,	'Honda                                             ',	'Motora                                            '),
(3,	'Honda                                             ',	'Motora                                            '),
(4,	'Honda                                             ',	'Motora                                            '),
(5,	'Honda                                             ',	'Motora                                            '),
(6,	'Honda                                             ',	'Motora                                            '),
(7,	'Honda                                             ',	'Motora                                            '),
(8,	'Honda                                             ',	'Motora                                            ');

ALTER TABLE ONLY "public"."many_persons_has_many_addresses" ADD CONSTRAINT "addresses_fk" FOREIGN KEY (id_addresses) REFERENCES addresses(id) MATCH FULL ON UPDATE CASCADE ON DELETE RESTRICT NOT DEFERRABLE;
ALTER TABLE ONLY "public"."many_persons_has_many_addresses" ADD CONSTRAINT "persons_fk" FOREIGN KEY (id_persons) REFERENCES persons(id) MATCH FULL ON UPDATE CASCADE ON DELETE RESTRICT NOT DEFERRABLE;

-- 2024-12-01 12:41:30.583706+00
