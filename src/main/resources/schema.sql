create table if not exists _user(
	id serial not null primary key,
	username varchar(30) not null unique,
	password text not null,
	email varchar(50) unique
);

create table if not exists customer(
	id serial not null primary key references _user(id),
	first_name varchar(50) not null,
	last_name varchar(70) not null,
	delivery_address text
);

create table if not exists company(
	id serial not null primary key references _user(id),
	name varchar(128) not null unique,
	description text
);

create table if not exists device(
	id serial not null primary key,
	name varchar(50) not null,
	type varchar(128),
	description text,
	price int not null,
	company_id int not null references company(id),
	unique (name, company_id)
);

create table if not exists basket(
	id serial not null primary key,
	customer_id int not null references customer(id) unique
);

create table if not exists devices_in_basket(
	id serial not null primary key,
	basket_id int references basket(id),
	device_id int references device(id),
	amount int not null default 1,
	unique (basket_id, device_id)
);

create table if not exists transaction(
	id serial not null primary key,
	customer_id int not null references _user(id),
	seller_id int not null references company(id),
	device_id int not null references device(id),
	amount int not null,
	date_time timestamp default current_timestamp
);