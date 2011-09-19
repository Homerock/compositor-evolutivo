-- begin;

create user compositordb WITH password 'hendrix';
CREATE DATABASE compositordb;
GRANT ALL PRIVILEGES ON DATABASE compositordb to compositordb;


--rollback;