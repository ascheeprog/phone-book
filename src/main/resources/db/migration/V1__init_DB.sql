

create table users(
id serial primary key,
first_name varchar(255) not null,
last_name varchar(255)
);

create table contact(
id serial primary key,
first_name varchar(255),
last_name varchar(255),
phone varchar(16) not null,
email varchar(255),
users_id integer references users (id)
);


