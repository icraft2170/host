CREATE TABLE host
(
    host_id           BIGINT AUTO_INCREMENT NOT NULL,
    name              VARCHAR(255)          NULL,
    address           VARCHAR(255)          NULL,
    registration_time datetime              NULL,
    modified_time     datetime              NULL,
    last_alive_time   datetime              NULL,
    status            VARCHAR(255)          NULL,
    CONSTRAINT pk_host PRIMARY KEY (host_id)
);

ALTER TABLE host
    ADD CONSTRAINT uc_host_address UNIQUE (address);

ALTER TABLE host
    ADD CONSTRAINT uc_host_name UNIQUE (name);