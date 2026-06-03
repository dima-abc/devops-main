--liquibase formatted sql
--changeset stepanovdn:001_create_results_table.sql
CREATE TABLE results (
    id SERIAL PRIMARY KEY,
    first_arg DECIMAL,
    second_arg DECIMAL,
    result DECIMAL,
    operation VARCHAR(50),
    create_date TIMESTAMP WITH TIME ZONE DEFAULT now()
);

--rollback DROP TABLE results;