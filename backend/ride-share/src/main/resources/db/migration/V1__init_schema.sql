create table authority (
    id        bigserial primary key,
    authority text
);

create table application_user (
    id                      bigserial primary key,
    username                text unique,
    password                text,
    mobile_number           text,
    account_non_expired     boolean,
    account_non_locked      boolean,
    credentials_non_expired boolean,
    provider                text,
    provider_id             text,
    enabled                 boolean,
    authority_id            bigint references authority (id)
);

create table ride (
    id                         bigserial primary key,
    origin_latitude            float,
    origin_longitude           float,
    destination_latitude       float,
    destination_longitude      float,
    is_door_to_door            boolean default false,
    departure_time             timestamp with time zone,
    is_departure_time_flexible boolean default false,
    price                      float,
    has_luggage_space          boolean default false,
    capacity                   integer default 0,
    is_instant_booking_enabled boolean default false,
    provider_id                bigint references application_user (id)
);

create table booking_status (
    id          bigserial primary key,
    name        text not null,
    pretty_name text not null
);

create table booking (
    id        bigserial primary key,
    status_id bigint references booking_status (id),
    booked_by bigint references application_user (id),
    ride_id   bigint references ride (id)
);

create table booking_request_status (
    id          bigserial primary key,
    name        text not null,
    pretty_name text not null
);

create table booking_request (
    id           bigserial primary key,
    status_id    bigint references booking_status (id),
    requested_by bigint references application_user (id),
    ride_id      bigint references ride (id)
);

create table rating (
    id       bigserial primary key,
    value    float not null,
    ride_id  bigint references ride (id),
    rated_by bigint references application_user (id)
);

create table message (
    id           bigserial primary key,
    timestamp    timestamp with time zone default now(),
    content      text not null,
    recipient_id bigint references application_user (id),
    sender_id    bigint references application_user (id)
);