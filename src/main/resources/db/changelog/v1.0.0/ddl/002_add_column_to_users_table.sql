--liquibase formatted sql
--changeset dstepanov:v1.0.0/002_add_column)to_users_table.sql
ALTER TABLE users
    ADD COLUMN first_arg BIGINT;
ALTER TABLE users
    ADD COLUMN second_arg BIGINT;
ALTER TABLE users
    ADD COLUMN result bigint;

COMMENT ON TABLE users IS 'Список пользователей';
COMMENT ON COLUMN users.id IS 'Идентификатор пользователя';
COMMENT ON COLUMN users.username IS 'Имя пользователя';
COMMENT ON COLUMN users.first_arg IS 'Первый аргумент';
COMMENT ON COLUMN users.second_arg IS 'Второй аргумент';
COMMENT ON COLUMN users.result IS 'Результат вычислительной операции';