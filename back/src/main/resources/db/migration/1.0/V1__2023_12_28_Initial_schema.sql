--date: 2023-12-28
--author: tadanoluka


DROP TABLE IF EXISTS wagons;
DROP TABLE IF EXISTS user_operation_stations_accesses;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS freights;
DROP TABLE IF EXISTS freights_groups;
DROP TABLE IF EXISTS consignees;
DROP TABLE IF EXISTS stations;
DROP TABLE IF EXISTS wagon_statuses;

CREATE TABLE freights_groups
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL UNIQUE
);

CREATE TABLE freights
(
    id         SERIAL PRIMARY KEY,
    etsng      INT          NOT NULL UNIQUE,
    name       VARCHAR(256) NOT NULL,
    short_name VARCHAR(64)  NOT NULL,
    group_id   INT          NOT NULL REFERENCES freights_groups (id)
);

CREATE TABLE consignees
(
    id INT PRIMARY KEY
);

CREATE TABLE stations
(
    id         SERIAL PRIMARY KEY,
    esr        INT          NOT NULL UNIQUE,
    name       VARCHAR(128) NOT NULL,
    short_name VARCHAR(64)  NOT NULL
);

CREATE TABLE wagon_statuses
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL UNIQUE
);

CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL UNIQUE
);

CREATE TABLE users
(
    id        BIGSERIAL PRIMARY KEY,
    username  VARCHAR(32) NOT NULL UNIQUE,
    firstname VARCHAR(64) NOT NULL,
    lastname  VARCHAR(64) NOT NULL,
    role_id   INT         NOT NULL REFERENCES roles (id)
);

CREATE TABLE user_operation_stations_accesses
(
    user_id            BIGINT NOT NULL REFERENCES users (id),
    allowed_station_id INT    NOT NULL REFERENCES stations (id),
    PRIMARY KEY (user_id, allowed_station_id)
);

CREATE TABLE wagons
(
    id                     BIGINT PRIMARY KEY,
    destination_station_id INT       NOT NULL REFERENCES stations (id),
    operation_station_id   INT       NOT NULL REFERENCES stations (id),
    freight_id             INT       NOT NULL REFERENCES freights (id),
    consignee_id           INT       NOT NULL REFERENCES consignees (id),
    weight                 INT       NOT NULL,
    wagon_status_id        INT       NOT NULL REFERENCES wagon_statuses (id),
    updated_by_user_id     BIGINT    NOT NULL REFERENCES users (id),
    created_at             TIMESTAMP NOT NULL DEFAULT NOW(),
    last_updated_at        TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE OR REPLACE FUNCTION update_modified_column()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.last_updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE TRIGGER update_modified_time
    BEFORE UPDATE
    ON wagons
    FOR EACH ROW
EXECUTE PROCEDURE update_modified_column();