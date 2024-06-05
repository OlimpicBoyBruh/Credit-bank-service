CREATE TABLE statement (
    creation_date timestamp(6),
    sign_date timestamp(6),
    client_id uuid UNIQUE,
    credit_id uuid UNIQUE,
    statement_id uuid NOT NULL,
    ses_code varchar(255),
    status varchar(255),
    applied_offer jsonb,
    status_history jsonb,
    PRIMARY KEY (statement_id)
);

ALTER TABLE IF EXISTS statement ADD CONSTRAINT FK_credit_id FOREIGN KEY (credit_id) REFERENCES credit;
ALTER TABLE IF EXISTS statement ADD CONSTRAINT FK_client_id FOREIGN KEY (client_id) REFERENCES client;