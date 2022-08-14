CREATE USER vote_api_user WITH PASSWORD 'vote_api_user' CREATEDB;

CREATE DATABASE vote_api_db_dev;
GRANT ALL PRIVILEGES ON DATABASE vote_api_db_dev TO vote_api_user;
