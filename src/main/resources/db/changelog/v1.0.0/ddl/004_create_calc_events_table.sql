--liquibase formatted sql
--changeset dstepanov:v1.0.0/004_create_calc_events_table.sql
CREATE TABLE calc_events(
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    first BIGINT,
    second BIGINT,
    result BIGINT,
    create_date TIMESTAMP WITH TIME ZONE DEFAULT now(),
    type varchar(50)
)