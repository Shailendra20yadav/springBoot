create schema if not exists umgmt;
USE `umgmt`;
drop table IF EXISTS authorities;
drop table IF EXISTS users;
drop table IF EXISTS role_master;
create table  IF NOT EXISTS users (
	userid BIGINT not null primary key AUTO_INCREMENT ,
	username varchar(250) not null unique,
	password varchar(250) not null,
	enabled boolean not null,
	created_on DATETIME,
	created_by BIGINT,
	updated_on DATETIME,
	updated_by BIGINT
	
	
);

create table if not exists role_master(
	roleid BIGINT not null primary key AUTO_INCREMENT,
	role_name varchar(250) not null,
	role_desc varchar(250),
	created_on DATETIME,
	created_by BIGINT,
	updated_on DATETIME,
	updated_by BIGINT
);

create unique index ix_role_master_rolename on role_master (role_name);

create table if not exists authorities(
	auth_id BIGINT not null primary key AUTO_INCREMENT ,
	userid BIGINT not null,
	roleid BIGINT not null,
	created_on DATETIME,
	created_by BIGINT,
	updated_on DATETIME,
	updated_by BIGINT,
	constraint fk_authorities_users foreign key(userid) references users(userid),
	constraint fk_authorities_role_master foreign key(roleid) references role_master(roleid)
);
create unique index ix_auth_username on authorities (userid,roleid);
insert into users(userid,username,password,enabled,created_on,created_by) values(1,'admin','$2a$10$hbxecwitQQ.dDT4JOFzQAulNySFwEpaFLw38jda6Td.Y/cOiRzDFu',true,'2012-01-07 16:25:00',1);
insert into role_master(roleid,role_name,role_desc,created_on,created_by) values(1,'ROLE_ADMIN','Admin role','2012-01-07 16:25:00',1);
insert into authorities(auth_id,userid,roleid,created_on,created_by) values(1,1,1,'2012-01-07 16:25:00',1);
ALTER TABLE users AUTO_INCREMENT=100;
ALTER TABLE authorities AUTO_INCREMENT=100;
ALTER TABLE role_master AUTO_INCREMENT=10;
#String encoded=new BCryptPasswordEncoder().encode("admin@123");
#System.out.println(encoded);