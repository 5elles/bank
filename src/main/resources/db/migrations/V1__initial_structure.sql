use bank;

create table person
(
    id              int primary key       not null auto_increment,
    first_name      char(50)              not null,
    middle_name     char(50),
    last_name       char(50)              not null,
    dob             date                  not null,
    citizenID       char(50) unique       not null,
    passport_number char(50) unique       not null,
    isStaff         boolean default false not null,
    isClient        boolean default false not null
);


create table country
(
    id           int primary key auto_increment not null,
    country_name char(100) not null unique
);

create table region
(
    id          int auto_increment not null,
    country_id  int,
    region_name char(100) not null unique,

    primary key (id),
    foreign key (country_id) references country (id)
);

create table settlement_type
(
    id              int primary key auto_increment not null,
    settlement_type char(20) not null unique
);

create table settlement
(
    id                 int auto_increment not null,
    region_id          int,
    settlement_type_id int,
    settlement_name    char(50),

    primary key (id),
    foreign key (region_id) references region (id),
    foreign key (settlement_type_id) references settlement_type (id)
);

create table street_type
(
    id          int primary key auto_increment not null,
    street_type char(20) not null unique
);

create table address
(
    id               int auto_increment not null,
    settlement_id    int,
    street_type_id   int,
    street_name      char(50),
    house_number     char(20),
    apartment_number char(10),
    person_id        int,

    primary key (id),
    foreign key (settlement_id) references settlement (id),
    foreign key (street_type_id) references street_type (id),
    foreign key (person_id) references person (id)
);

create table contact
(
    id        int auto_increment not null,
    person_id int,

    primary key (id),
    foreign key (person_id) references person (id)
);

create table phone
(
    id           int primary key not null auto_increment,
    phone_number char(15) unique not null,
    contact_id   int,

    foreign key (contact_id) references contact (id)
);

create table email
(
    id         int primary key not null auto_increment,
    email      char(30) unique not null,
    contact_id int,

    foreign key (contact_id) references contact (id)
);

create table clearence_level
(
    id                           int primary key       not null auto_increment,
    level_name                   char(15) unique,
    has_safe_deposite_access     boolean default false not null,
    has_banking_records_access   boolean default false not null,
    has_personnel_records_access boolean default false not null,
    has_storage_rooms_access     boolean default false not null
);

create table position
(
    id                 int             not null auto_increment,
    position_name      char(50) unique not null,
    clearence_level_id int             not null,

    primary key (id),
    foreign key (clearence_level_id) references clearence_level (id)
);

create table staff
(
    id               int  not null auto_increment,
    person_id        int  not null,
    position_id      int  not null,
    employment_date  date not null,
    termination_date date,

    primary key (id),
    foreign key (person_id) references person (id),
    foreign key (position_id) references bank.position (id)
);

create table customer
(
    id               int  not null auto_increment,
    person_id        int  not null,
    agreement_number char(20) unique,
    agreement_date   date not null,
    closure_date     date,

    primary key (id),
    foreign key (person_id) references person (id)
);

create table currency
(
    id                    int      not null auto_increment,
    currency_name         char(20) not null,
    currency_abbreviation char(3),
    currency_code         char(3)  not null unique,
    country_id            int      not null,

    primary key (id),
    foreign key (country_id) references country (id)
);

create table bank_account
(
    id              int      not null auto_increment,
    customer_id     int      not null,
    account_number  char(30) not null unique,
    currency_id     int      not null,
    openning_date   date     not null,
    closure_date    date,
    current_balance decimal(20, 2) default 0.00,

    primary key (id),
    foreign key (customer_id) references customer (id),
    foreign key (currency_id) references currency (id)
);

create table payment_order
(
    id              int                                      not null auto_increment,
    date_and_time   timestamp      default current_timestamp not null,
    amount          decimal(20, 2) default 0.00              not null,
    from_account_id int                                      not null,
    to_account_id   int                                      not null,
    primary key (id),
    foreign key (from_account_id) references bank_account (id),
    foreign key (to_account_id) references bank_account (id)
);

create table operations_register
(
    id               int not null auto_increment,
    payment_order_id int,

    primary key (id),
    foreign key (payment_order_id) references payment_order (id)
);
