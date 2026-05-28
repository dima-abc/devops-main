--liquibase formatted sql
--changeset dstepanov:v1.0.0/001_create_users_table.sql
CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(2000)
);