CREATE TABLE user_settings
(
    id BIGSERIAL PRIMARY KEY,
    enable_notification BOOLEAN,
    language VARCHAR(255),
    user_id int8 REFERENCES users(id)
);

ALTER TABLE users ADD CONSTRAINT unique_telegram_id UNIQUE(telegram_id);

ALTER TABLE users ADD COLUMN user_settings_id bigint;

ALTER TABLE users ADD CONSTRAINT user_settings_id_constraint FOREIGN KEY (user_settings_id) REFERENCES user_settings;
