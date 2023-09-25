use bank;
alter table settlement_type
    add column short_name char(10) not null unique;


alter table street_type
    add column short_name char(10) not null unique;
