drop table if exists db_request;
drop table if exists db_event;
drop table if exists db_task;
drop table if exists db_init_time;
drop table if exists db_procedure;
drop table if exists db_account;

create table db_request (id int primary key auto_increment,
                         overview text, header text, body text);

insert into db_request (id, overview, header, body) values (1,
                                                            '{"method":"get","url":"https://www.baidu.com/s"}',
                                                            '{"User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36 Edg/88.0.705.56"}',
                                                            '{"ie":"UTF-8","wd":"albert"}');

insert into db_request (id, overview, header, body) values (2,
                                                            '{"method":"get","url":"https://www.cnblogs.com/"}',
                                                            '{"User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36 Edg/88.0.705.56"}',
                                                            '{}');

insert into db_request (id, overview, header, body) VALUES (3,
                                                            '', '',
                                                            '[{"method":"xpath","name":"link","selector":"//*[@id=\\"post_list\\"]/article/section/div/a/@href"},{"method":"xpath","name":"name","selector":"//*[@id=\\"post_list\\"]/article/section/div/a/allText()"}]');

insert into db_request (id, overview, header, body) VALUES (4,
                                                            '', '{"channelTitle":"channel标题","channelUrl":"channel链接","channelDescription":"channel说明"}',
                                                            '{"titleAlias":"name","authorAlias":"author","textAlias":"text","urlAlias":"link"}');

insert into db_request (id, overview, header, body) VALUES (5, '', '', '');

create table db_event (id int primary key auto_increment, generatedTime datetime, belongedTaskName varchar(20), successful boolean,
                       type varchar(20), overview varchar(100), header text, body longtext);

create table db_task (id int primary key auto_increment, type int, name text, requestId int,
                      inputTaskId int, inputEventProperty varchar(20), outputEventId int);

insert into db_task (id, type, name, requestId)VALUES (1, 0, '百度搜索Albert', 1);

insert into db_task (id, type, name, requestId)VALUES (2, 0, '博客园首页', 2);

insert into db_task (id, type, name, requestId, inputTaskId, inputEventProperty)VALUES (3, 1,
                                                                                        '解析博客园首页', 3, 2, 'body');

insert into db_task (id, type, name, requestId, inputTaskId, inputEventProperty)VALUES (4, 2,
                                                                                        '生成博客园rss', 4, 3, 'body');

insert into db_task (id, type, name, requestId, inputTaskId, inputEventProperty) VALUES (5, 3,
                                                                                         '对外暴露rss访问接口', 5, 4, 'body');

create table db_procedure (id int primary key auto_increment, name varchar(20), entryTaskId int, lastExecuteDateTime datetime,
                           month int, day int, weekDay int, hour int, minute int, dayInterval int, hourInterval int, minuteInterval int);

insert into db_procedure (id, name, entryTaskId, lastExecuteDateTime, month, day, weekDay, hour, minute, dayInterval, hourInterval, minuteInterval)
values (1, '第一个流程', 2, null, -1, -1, -1, 12, 0, -1, -1, -1);