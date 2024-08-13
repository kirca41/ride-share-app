create table authority(
    id bigserial primary key,
    authority text
);

create table application_user(
    id bigserial primary key,
    username text unique,
    password text,
    mobile_number text,
    account_non_expired boolean,
    account_non_locked boolean,
    credentials_non_expired boolean,
    provider text,
    provider_id text,
    enabled boolean,
    authority_id bigint references authority(id)
);