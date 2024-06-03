create table client (
birthdate date,
 dependent_amount integer,
 client_id uuid not null,
  account_number varchar(255), email varchar(255),
   first_name varchar(255),
    gender varchar(255) check (gender in ('MALE','FEMALE','NOT_BINARY')),
     last_name varchar(255),
      marital_status varchar(255) check (marital_status in ('SINGLE','MARRIED','DIVORCED','WIDOWED')),
       middle_name varchar(255),
        passport jsonb,
         primary key (client_id));