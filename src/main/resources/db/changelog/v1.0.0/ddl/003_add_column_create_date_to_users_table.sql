--liquibase formatted sql
--changeset dstepanov:v1.0.0/003_add_column_create_date_to_users_table.sql
ALTER TABLE users
    ADD COLUMN create_date TIMESTAMP WITH TIME ZONE DEFAULT now();
--rollback ALTER TABLE users DROP COLUMN create_date;