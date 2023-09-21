create schema bank;
use bank;

create table country(
id int primary key auto_increment not null,
country_name char(100) not null unique
);

create table region(
id int auto_increment not null,
country_id int,
region_name char(100) not null unique,

primary key (id),
foreign key (country_id) 
	references country (id)
);

create table zip_code(
id int auto_increment not null,
region_id int,
zip_code char(20) not null unique,

primary key (id),
foreign key (region_id)
	references region(id)
);

create table city(
id int auto_increment not null,
region_id int,
city_name char(50) not null unique,

primary key (id),
foreign key (region_id)
	references region(id)
);

create table district(
id int auto_increment not null,
region_id int,
district_name char(50) not null unique,

primary key (id),
foreign key (region_id)
	references region(id)
);

create table village_type(
id int primary key auto_increment not null,
village_type_name char(20) not null unique
);

create table town_type(
id int primary key auto_increment not null,
town_type_name char(20) not null unique
);

create table street_type(
id int primary key auto_increment not null,
street_type_name varchar(20) not null unique
);

create table town (
id int auto_increment not null,
district_id int,
town_type_id int,
town_name char(50),

primary key (id),
foreign key (district_id) references district (id),
foreign key (town_type_id) references town_type (id)
);

create table village (
id int auto_increment not null,
district_id int,
village_type_id int,
village_name char(50),

primary key (id),
foreign key (district_id) references district(id),
foreign key (village_type_id) references village_type(id)
);

create table street (
id int auto_increment not null,
city_id int, 
village_id int, 
town_id int,
street_type_id int,
street_name char (50),

primary key (id),
foreign key (city_id) references city(id),
foreign key (village_id) references village(id),
foreign key (town_id) references town(id),
foreign key (street_type_id) references street_type(id)
);

create table address (
id int auto_increment not null,
country_id int,
zip_code_id int,
region_id int,
city_id int,
district_id int,
town_id int,
village_id int,
street_id int,
house_number char(20),
apartment_number char(10),

primary key (id),
foreign key (country_id) references country(id),
foreign key (zip_code_id) references zip_code(id),
foreign key (region_id) references region(id),
foreign key (city_id) references city(id),
foreign key (district_id) references district(id),
foreign key (town_id) references town(id),
foreign key (village_id) references village(id),
foreign key (street_id) references street(id)
);

create table phone (
id int primary key not null auto_increment,
phone_number char(15) unique not null 
);

create table email (
id int primary key not null auto_increment,
email char(30) unique not null 
);

create table contact (
id int auto_increment not null,
phone_id int not null,
phone2_id int,
phone3_id int,
email_id int,
email2_id int,

primary key (id),
foreign key (phone_id) references phone(id),
foreign key (phone2_id) references phone(id),
foreign key (phone3_id) references phone(id),
foreign key (email_id) references email(id),
foreign key (email2_id) references email(id)
);

create table person (
id int not null auto_increment,
first_name char(50) not null,
middle_name char(50),
last_name char(50) not null,
dob date not null,
citizenID char(50) unique not null,
passport_number char(50) unique not null,
isStaff boolean default false not null,
isClient boolean default false not null,
contact_id int,
address_id int,

primary key (id),
foreign key (contact_id) references contact(id),
foreign key (address_id) references address(id)
);

create table clearence_level(
id int primary key not null auto_increment,
level_name char(15) unique,
has_safe_deposite_access boolean default false not null,
has_banking_records_access boolean default false not null,
has_personnel_records_access boolean default false not null,
has_storage_rooms_access boolean default false not null
);

create table position (
id int not null auto_increment,
position_name char(50) unique not null,
clearence_level_id int not null,

primary key (id),
foreign key (clearence_level_id) references clearence_level(id)
);

create table staff (
id int not null auto_increment,
person_id int not null,
position_id int not null,
employment_date date not null,
termination_date date,

primary key (id),
foreign key (person_id) references person(id),
foreign key (position_id) references bank.position(id)
);

create table customer(
id int not null auto_increment,
person_id int not null,
agreement_number char(20) unique,
agreement_date date not null,
closure_date date,

primary key (id),
foreign key (person_id) references person(id)
);

create table currency(
id int not null auto_increment,
currency_name char(20) not null,
currency_abbreviation char(3),
currency_code char(3) not null unique,
country_id int not null,

primary key (id),
foreign key (country_id) references country(id)
);

create table bank_account(
id int not null auto_increment,
customer_id int not null,
account_number char(30) not null unique,
currency_id int not null,
openning_date date not null,
closure_date date,
current_balance decimal (20, 2) default 0.00,

primary key (id),
foreign key (customer_id) references customer(id),
foreign key (currency_id) references currency(id)
);

create table payment_order(
id int not null auto_increment,
date_and_time timestamp default current_timestamp not null,
amount decimal (20, 2) default 0.00 not null,
from_account_id int not null,
to_account_id int not null,
primary key (id),
foreign key (from_account_id) references bank_account(id),
foreign key (to_account_id) references bank_account(id)
);

create table operations_register(
id int not null auto_increment,
payment_order_id int,

primary key (id),
foreign key (payment_order_id) references payment_order(id)
);








