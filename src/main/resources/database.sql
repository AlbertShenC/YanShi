drop table if exists db_request;
drop table if exists db_event;
drop table if exists db_task;
drop table if exists db_procedure;
drop table if exists db_account;

create table db_request (id int primary key auto_increment, type varchar(20),
                         overview text, header text, body text);

insert into db_request (id, type, overview, header, body) values (1, 'HttpRequest',
                                                                  'get https://www.baidu.com/s',
                                                                  'User-Agent= Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36 Edg/88.0.705.56',
                                                                  'ie=UTF-8&wd=albert');

create table db_event (id int primary key auto_increment, generatedTime datetime, successful boolean,
                       type varchar(20), overview varchar(100), header text, body longtext);

create table db_task (id int primary key auto_increment, type varchar(20), requestId int, outputEventId int, belongedProcedure int);

