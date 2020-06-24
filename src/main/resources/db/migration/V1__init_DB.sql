create table users(
id int auto_increment primary key,
first_name varchar(255) not null,
last_name varchar(255)
);

create table contact(
id int auto_increment primary key,
first_name varchar(255),
last_name varchar(255),
phone varchar(16) not null,
email varchar(255),
users_id int references users (id)
    on delete cascade
);


