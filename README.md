# Phone Book

Phone Book представляет собой пример телефонной книги, реализованной в виде backend приложения, выполненого в стиле Rest с использованием фрэймворка Spring.
#### Функционал ####
 1.	Пользователь
  + Добавление, удаление, изменение пользователя(имя,фамилия)
  + Поиск по имени или по id пользователя
  + Список всех пользователей
 2. Контакт
  + Добавление, удаление, изменение контакта(номер телефона, имя, фамилия, email)
  + Поиск по номеру телефона или по id контакта для телефонной книги пользователя
  + Список всех контактов пользователя
#### Запуск приложения ####
Для начала создать БД с таблицами:
<create table users(
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
);>
