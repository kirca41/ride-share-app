create table authority (
    id        bigserial primary key,
    authority text
);

create table ride_share_user (
    id                      bigserial primary key,
    first_name              text,
    last_name               text,
    username                text unique,
    password                text,
    mobile_number           text,
    account_non_expired     boolean,
    account_non_locked      boolean,
    credentials_non_expired boolean,
    provider                text,
    provider_id             text,
    enabled                 boolean,
    joined_on               timestamp with time zone default now(),
    authority_id            bigint references authority (id)
);

create table ride (
    id                         bigserial primary key,
    uuid                       uuid,
    origin                     text,
    origin_latitude            float,
    origin_longitude           float,
    destination                text,
    destination_latitude       float,
    destination_longitude      float,
    is_door_to_door            boolean default false,
    departure_date_time        timestamp with time zone,
    is_departure_time_flexible boolean default false,
    price                      float,
    has_luggage_space          boolean default false,
    capacity                   integer default 0,
    is_instant_booking_enabled boolean default false,
    is_canceled                boolean default false,
    provider_id                bigint references ride_share_user (id)
);

create table booking_status (
    id          bigserial primary key,
    name        text not null,
    pretty_name text not null
);

create table booking (
    id           bigserial primary key,
    seats_booked integer,
    booked_at    timestamp with time zone default now(),
    status_id    bigint references booking_status (id),
    booked_by    bigint references ride_share_user (id),
    ride_id      bigint references ride (id)
);

create table rating (
    id       bigserial primary key,
    value    float not null,
    comment  text,
    ride_id  bigint references ride (id),
    rated_by bigint references ride_share_user (id)
);

create table chat (
    id               bigserial primary key,
    uuid             uuid,
    participant_1_id bigint references ride_share_user (id),
    participant_2_id bigint references ride_share_user (id)
);

create table message (
    id           bigserial primary key,
    timestamp    timestamp with time zone default now(),
    content      text not null,
    chat_id      bigint references chat (id),
    sender_id    bigint references ride_share_user (id)
);

create table notification (
    id            bigserial primary key,
    type          text,
    subject       text,
    recipient     text,
    template_name text,
    context       jsonb,
    is_processed  boolean default false
);