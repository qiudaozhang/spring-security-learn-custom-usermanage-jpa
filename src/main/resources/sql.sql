-- drop DATABASE spring_security;
-- create DATABASE spring_security;
-- use spring_security;
-- show tables;
insert into user_auth (username,password) values('admin','123');


INSERT INTO spring_security.authority (authority, description, uri) VALUES ('all', '所有', '/**');
INSERT INTO spring_security.authority (authority, description, uri) VALUES ('read', '读取', '/read');
INSERT INTO spring_security.authority (authority, description, uri) VALUES ('write', '写入', '/write');


insert into user_auth_authority (user_auth_id, authority_id)
values(1,1);