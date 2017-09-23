-- CREATE ROLE
-- CREATE DATABASE blogdb;
CREATE TABLE users(
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(64),
    password VARCHAR(64),
    create_time timestamp
);


CREATE TABLE article(
    id SERIAL PRIMARY KEY,
    title VARCHAR(64),
    body VARCHAR(1024),
    user_id INTEGER,
    create_time timestamp
);

CREATE TABLE comment(
    id SERIAL PRIMARY KEY,
    article_id Integer,
    comment VARCHAR(1024),
    create_time timestamp
);

grant select ,update ,insert ,delete on table article TO postgres;
grant select ,update ,insert ,delete on table comment TO postgres;
grant select ,update ,insert ,delete on table users TO postgres;

grant select ,update on article_id_seq TO postgres;
grant select ,update on comment_id_seq TO postgres;
grant select ,update on users_id_seq TO postgres;
