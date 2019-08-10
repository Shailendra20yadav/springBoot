create schema if not exists umgmt;
USE `umgmt`;
create table  IF NOT EXISTS users (
	userid BIGINT not null primary key AUTO_INCREMENT,
	username varchar(250) not null unique,
	password varchar(250) not null,
	enabled boolean not null
	
);
create table if not exists authorities(
	auth_id BIGINT not null primary key AUTO_INCREMENT,
	username varchar(250) not null,
	authority varchar(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);
insert into users(userid,username,password,enabled) values(1,'admin','$2a$10$hbxecwitQQ.dDT4JOFzQAulNySFwEpaFLw38jda6Td.Y/cOiRzDFu',true);
insert into authorities(auth_id,username,authority) values(1,'admin','ROLE_ADMIN');
ALTER TABLE users AUTO_INCREMENT=1000;
ALTER TABLE authorities AUTO_INCREMENT=1000;
#String encoded=new BCryptPasswordEncoder().encode("admin@123");
#System.out.println(encoded);