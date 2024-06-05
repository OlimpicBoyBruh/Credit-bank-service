CREATE TABLE credit (
    amount numeric(38,2),
    is_insurance_enabled boolean NOT NULL,
    is_salary_client boolean NOT NULL,
    monthly_payment numeric(38,2),
    psk numeric(38,2),
    rate numeric(38,2),
    term integer,
    credit_id uuid NOT NULL,
    credit_status varchar(255) CHECK (credit_status IN ('CALCULATED', 'ISSUED')),
    payment_schedule jsonb,
    PRIMARY KEY (credit_id)
);