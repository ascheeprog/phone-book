create table users(
    id varchar(64) primary key,
    nickname varchar(50) unique,
    first_name varchar(50),
    last_name varchar(50),
    creation_time timestamp
);


create table contact(
    id varchar(64) primary key,
    first_name varchar(255),
    last_name varchar(255),
    phone varchar(50) not null,
    email varchar(255),
    url varchar(1000),
    user_id varchar(64) references users (id)
        on delete cascade
);

create table company(
    id varchar(64) primary key,
    name varchar(50),
    email varchar(50),
    phone varchar(25),
    url_logo varchar(1000),
    user_id varchar(64) references users (id)
        on delete cascade,
    contact_id varchar(64) references contact (id)
        on delete cascade
);

create table messages(
    id bigint auto_increment primary key,
    message varchar(500) not null,
    replay_to varchar(64) references messages (id),
    user_id varchar(64) references users (id),
    message_status_code varchar(10),
    creation_time timestamp
);
create table groups(
    id varchar(64) primary key,
    name varchar(50) not null,
    creation_time timestamp,
    group_status_code varchar(10)
);

create table groups_users(
    user_id varchar(64) references users (id),
    group_id varchar(64) references groups (id)
);