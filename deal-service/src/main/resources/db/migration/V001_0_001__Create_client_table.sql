CREATE TABLE client (
    birthdate date,
    dependent_amount integer,
    client_id uuid NOT NULL,
    account_number varchar(255),
    email varchar(255),
    first_name varchar(255),
    gender varchar(255) CHECK (gender IN ('MALE', 'FEMALE', 'NOT_BINARY')),
    last_name varchar(255),
    marital_status varchar(255) CHECK (marital_status IN ('SINGLE', 'MARRIED', 'DIVORCED', 'WIDOWED')),
    middle_name varchar(255),
    passport jsonb,
    PRIMARY KEY (client_id)
);