-- drop DATABASE spring_security;
-- create DATABASE spring_security;
-- use spring_security;
-- show tables;
insert into user_auth (username,password,uid) values('admin','$2a$10$X5Uqkt9lEzl.x3D4ZmvQGOVq8TNiHT4kYd66BYcqOrwZOvjg64Frq','791915576020873216');


INSERT INTO spring_security.authority (authority, description, uri) VALUES ('all', '所有', '/**');
INSERT INTO spring_security.authority (authority, description, uri) VALUES ('read', '读取', '/read');
INSERT INTO spring_security.authority (authority, description, uri) VALUES ('write', '写入', '/write');


insert into user_auth_authority (user_auth_id, authority_id)
values(791915576020873216,1);