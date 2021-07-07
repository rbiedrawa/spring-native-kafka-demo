create table customer
(
    id     serial primary key,
    user_id uuid         not null,
    name   varchar(255) not null,
    email  varchar(255) not null
);