create table demo
(
    id bigint primary key auto_increment,
    name varchar(30) not null
);

create table user
(
    id bigint primary key auto_increment,
    username varchar(30) not null,
    password varchar(64) not null
);