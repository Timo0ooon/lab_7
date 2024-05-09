CREATE TABLE Users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255),
    hash_password VARCHAR(32)
);