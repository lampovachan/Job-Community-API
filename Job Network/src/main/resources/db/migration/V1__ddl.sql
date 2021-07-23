CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(100) UNIQUE NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(100),
    last_name  VARCHAR(100),
    password   VARCHAR(255) NOT NULL,
    cv_url     VARCHAR(255),
    photo_url  VARCHAR(255)
);

CREATE TABLE experience
(
    id         BIGSERIAL,
    company_id BIGINT,
    user_id    BIGINT,
    start_date TIMESTAMP,
    end_date   TIMESTAMP
);

CREATE TABLE companies
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(255) UNIQUE
);

CREATE TABLE photos
(
    company_id BIGINT,
    id         SERIAL PRIMARY KEY,
    url        VARCHAR(255)
);

ALTER TABLE experience
    ADD CONSTRAINT fk_companies_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE photos
    ADD CONSTRAINT fk_photos_companies FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE experience
    ADD CONSTRAINT fk_companies_experience FOREIGN KEY (company_id) REFERENCES companies (id) ON DELETE RESTRICT ON UPDATE CASCADE;


