insert into users(id, nickname, first_name, last_name)
values ('test_user_1', 'JSNow', 'John', 'Snow'),
('test_user_2', 'JhonW','John', 'Wick');

insert into contact(id, first_name, last_name, phone, email, url, user_id)
values
('test_contact_1', 'Brandon', 'Stark', '3123456789', 'brain_star@gmail.com', 'some_url.com', 'test_user_1'),
('test_contact_2', 'Cersei', 'Lannister','5321456789', 'I_Am_Queen@queenmail.com', 'some_url.com', 'test_user_1'),
('test_contact_3', 'Deise', 'Dog', '9752136987', 'deise@dogmail.com', 'some_url.com', 'test_user_2'),
('test_contact_4', 'Winston', 'Troll', '5321456789', 'winston@gmail.com', 'some_url.com', 'test_user_2');

insert into company(id, name, email, phone, url_logo, user_id)
values
('test_company_id', 'WinterFell', 'winter@company.com', '88005553535', 'callWinterIsTrueDamage.com', 'test_user_1');