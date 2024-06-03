create table credit (
amount numeric(38,2),
 is_insurance_enabled boolean not null,
  is_salary_client boolean not null,
   monthly_payment numeric(38,2),
   psk numeric(38,2),
    rate numeric(38,2),
     term integer,
      credit_id uuid not null,
       credit_status varchar(255) check (credit_status in ('CALCULATED','ISSUED')),
        payment_schedule jsonb,
         primary key (credit_id));