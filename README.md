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
Создать базу данных phonebook или свою базу данных и применить ее название в файле`src/main/resources/application.properties`
#### UPD #### 
Схемы создавать вручную не нужно, flyway сам накатывыает их в базу данных, поэтому просто достаточно создать БД.

 После настройки БД собрать приложение:
 ```
  mvn clean install
 ```
#### Тестирование ####
В данном приложении можно выбрать профиль на котором будет проводиться тестирование: `container` - профиль для тестирования БД в докер контейнере;`h2` - профлиль для тестирования БД на H2. По умолчанию используется `container`. Действующий профиль можно изменить в файле `src/test/resources/application.properties`

#### Api приложения ####
 ```
http://[yourhost]/phonebook-api.html
 ```
 
 
 P.S. тестирование в докер контейнере проводится на PostgreSQL, для тестирование на профиле `container` требуется настроить докер окружение
