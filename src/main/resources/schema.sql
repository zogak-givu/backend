create table if not exists member
(
    id    varchar(30)  not null primary key,
    name  varchar(30)  not null
);

create table if not exists wallet
(
    id    varchar(30)  not null primary key,
    address  varchar(60)  not null,
    member_id varchar(30) not null,
    constraint fk_member
        foreign key (member_id) references member (id)
);