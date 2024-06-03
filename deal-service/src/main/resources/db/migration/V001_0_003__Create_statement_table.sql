create table statement (
creation_date timestamp(6),
 sign_date timestamp(6),
  client_id uuid unique,
  credit_id uuid unique,
  statement_id uuid not null,
   ses_code varchar(255), status varchar(255),
    applied_offer jsonb,
    status_history jsonb,
     primary key (statement_id));

alter table if exists statement add constraint FKdrij5d3mdeb1hp56154m7c2mw foreign key (credit_id) references credit;
alter table if exists statement add constraint FKdmspwvw0ux234vtferxbd1q9u foreign key (client_id) references client;