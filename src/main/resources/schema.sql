create table if not exists member
(
    id    varchar(30)  not null primary key,
    name  varchar(30)  not null
);

create table if not exists donation_organization
(
    id    varchar(30)  not null primary key,
    name  varchar(60)  not null
);

create table if not exists wallet
(
    id    varchar(30)  not null primary key,
    address  varchar(60)  not null,
    member_id varchar(30) not null,
    organization_id varchar(30) not null,
    constraint fk_member
        foreign key (member_id) references member (id),
    constraint fk_organization
        foreign key (organization_id) references donation_organization (id)
);

