CREATE TABLE users
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  varchar(255) NOT NULL,
    last_name   varchar(255),
    telegram_id varchar(255) NOT NULL
);

CREATE TABLE dictionary
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users (id)
);

CREATE TABLE dictionary_item
(
    id              BIGSERIAL PRIMARY KEY,
    original_text   varchar(255) NOT NULL,
    picture         varchar(255) NOT NULL,
    translated_text varchar(255) NOT NULL,
    dictionary_id   BIGINT REFERENCES dictionary (id)
);


CREATE TABLE dictionary_dictionary_item
(
    dictionary_id BIGINT REFERENCES dictionary (id),
    items_id      BIGINT REFERENCES dictionary_item (id)

);

ALTER TABLE users
    ADD user_dictionary_id BIGINT REFERENCES dictionary (id) NOT NULL;
