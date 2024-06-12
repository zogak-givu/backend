DROP TABLE IF EXISTS wallet;
DROP TABLE IF EXISTS charity;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS donation_history;

create table if not exists member
(
    id    varchar(30)  not null primary key,
    name  varchar(30)  not null
);

create table if not exists charity
(
    id    varchar(30)  not null primary key,
    name  varchar(60)  not null,
    wallet_address  varchar(60)  not null
);

create table if not exists wallet
(
    id    varchar(30)  not null primary key,
    address  varchar(60)  not null,
    member_id varchar(30) not null,
    charity_id varchar(30) not null,
    constraint fk_member
        foreign key (member_id) references member (id),
    constraint fk_charity
        foreign key (charity_id) references charity (id)
);

create table if not exists donation_history
(
    id    varchar(70)  not null primary key,
    owner_address  varchar(50)  not null,
    user_address varchar(50) not null,
    charity_address varchar(50) not null,
    amount DECIMAL(18, 8) NOT NULL,
    timestamp DATETIME NOT NULL
);

insert into charity (id, name, wallet_address) values ('1', 'miral', '0xec04825e20883c6e65bec92f86b29551eb2df040');
insert into member (id, name) values ('3469515066', 'hum');
