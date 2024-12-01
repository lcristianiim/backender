--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0 (Debian 17.0-1.pgdg120+1)
-- Dumped by pg_dump version 17.0 (Debian 17.0-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: addresses; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.addresses OVERRIDING SYSTEM VALUE VALUES (1, '1,Cluj,12345,2                                    ', '12345                                             ');
INSERT INTO public.addresses OVERRIDING SYSTEM VALUE VALUES (2, '1,Cluj,12345,2                                    ', '12345                                             ');
INSERT INTO public.addresses OVERRIDING SYSTEM VALUE VALUES (3, '1,Cluj,12345,2                                    ', '12345                                             ');
INSERT INTO public.addresses OVERRIDING SYSTEM VALUE VALUES (4, '1,Cluj,12345,2                                    ', '12345                                             ');
INSERT INTO public.addresses OVERRIDING SYSTEM VALUE VALUES (5, '1,Cluj,12345,2                                    ', '12345                                             ');
INSERT INTO public.addresses OVERRIDING SYSTEM VALUE VALUES (6, '1,Cluj,12345,2                                    ', '12345                                             ');
INSERT INTO public.addresses OVERRIDING SYSTEM VALUE VALUES (7, '1,Cluj,12345,2                                    ', '12345                                             ');
INSERT INTO public.addresses OVERRIDING SYSTEM VALUE VALUES (8, '1,Cluj,12345,2                                    ', '12345                                             ');
INSERT INTO public.addresses OVERRIDING SYSTEM VALUE VALUES (9, '1,Cluj,12345,2                                    ', '12345                                             ');
INSERT INTO public.addresses OVERRIDING SYSTEM VALUE VALUES (10, '1,Cluj,12345,2                                    ', '12345                                             ');
INSERT INTO public.addresses OVERRIDING SYSTEM VALUE VALUES (11, '1,Cluj,12345,2                                    ', '12345                                             ');
INSERT INTO public.addresses OVERRIDING SYSTEM VALUE VALUES (12, '1,Cluj,12345,2                                    ', '12345                                             ');
INSERT INTO public.addresses OVERRIDING SYSTEM VALUE VALUES (13, '1,Cluj,12345,2                                    ', '12345                                             ');
INSERT INTO public.addresses OVERRIDING SYSTEM VALUE VALUES (14, '1,Cluj,12345,2                                    ', '12345                                             ');
INSERT INTO public.addresses OVERRIDING SYSTEM VALUE VALUES (15, '1,Cluj,12345,2                                    ', '12345                                             ');
INSERT INTO public.addresses OVERRIDING SYSTEM VALUE VALUES (16, '1,Cluj,12345,2                                    ', '12345                                             ');
INSERT INTO public.addresses OVERRIDING SYSTEM VALUE VALUES (17, '(1,Palermo,12345,2)                               ', '12345                                             ');


--
-- Data for Name: persons; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.persons OVERRIDING SYSTEM VALUE VALUES (1, 'Honda                                             ', 'Motora                                            ');
INSERT INTO public.persons OVERRIDING SYSTEM VALUE VALUES (2, 'Honda                                             ', 'Motora                                            ');
INSERT INTO public.persons OVERRIDING SYSTEM VALUE VALUES (3, 'Honda                                             ', 'Motora                                            ');
INSERT INTO public.persons OVERRIDING SYSTEM VALUE VALUES (4, 'Honda                                             ', 'Motora                                            ');
INSERT INTO public.persons OVERRIDING SYSTEM VALUE VALUES (5, 'Honda                                             ', 'Motora                                            ');
INSERT INTO public.persons OVERRIDING SYSTEM VALUE VALUES (6, 'Honda                                             ', 'Motora                                            ');
INSERT INTO public.persons OVERRIDING SYSTEM VALUE VALUES (7, 'Honda                                             ', 'Motora                                            ');
INSERT INTO public.persons OVERRIDING SYSTEM VALUE VALUES (8, 'Honda                                             ', 'Motora                                            ');
INSERT INTO public.persons OVERRIDING SYSTEM VALUE VALUES (9, 'Honda                                             ', 'Motora                                            ');


--
-- Data for Name: many_persons_has_many_addresses; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.many_persons_has_many_addresses VALUES (1, 1);
INSERT INTO public.many_persons_has_many_addresses VALUES (2, 2);
INSERT INTO public.many_persons_has_many_addresses VALUES (3, 3);
INSERT INTO public.many_persons_has_many_addresses VALUES (4, 4);
INSERT INTO public.many_persons_has_many_addresses VALUES (5, 5);
INSERT INTO public.many_persons_has_many_addresses VALUES (6, 6);
INSERT INTO public.many_persons_has_many_addresses VALUES (8, 8);
INSERT INTO public.many_persons_has_many_addresses VALUES (9, 17);


--
-- Name: addresses_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.addresses_id_seq', 17, true);


--
-- Name: persons_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.persons_id_seq', 9, true);


--
-- PostgreSQL database dump complete
--

