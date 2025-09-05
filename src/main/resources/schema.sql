create table if not exists pet
(
    id        int          not null auto_increment,
    name      varchar(255) not null,
    specie    varchar(255) not null,
    age       int          not null default 0,
    owner_name varchar(255),
    primary key (id)
);
