-- drop DATABASE spring_security;
-- create DATABASE spring_security;
-- use spring_security;
-- show tables;
insert into user_auth (username,password) values('admin','123');
insert into authority (authority,description,uri) value('all','所有','/**');
insert into user_auth_authority (user_auth_id, authority_id)
values(1,1);