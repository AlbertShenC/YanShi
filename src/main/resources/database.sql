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

insert into db_request (id, type, overview, header, body) values (2, 'HttpRequest',
                                                                  'get https://www.cnblogs.com/',
                                                                  'User-Agent= Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36 Edg/88.0.705.56',
                                                                  '');

insert into db_request (id, type, overview, header, body) VALUES (3, 'StringParser',
                                                                  'Xpath', 'link&name',
                                                                  '[{"xpath":"//*[@id=\\"post_list\\"]/article/section/div/a/@href"},{"xpath":"//*[@id=\\"post_list\\"]/article/section/div/a/allText()"}]');

insert into db_request (id, type, overview, header, body) VALUES (4, 'RssGenerate',
                                                                  '', 'name&author&text&link',
                                                                  'channel标题&channel链接&channel说明');

insert into db_request (id, type, overview, header, body) VALUES (5, 'RegisterForWebPage', '', '', '');

create table db_event (id int primary key auto_increment, generatedTime datetime, belongedTaskName varchar(20), successful boolean,
                       type varchar(20), overview varchar(100), header text, body longtext);

create table db_task (id int primary key auto_increment, type varchar(20), name text, requestId int,
                      preTaskId int, inputEventProperty varchar(20), outputEventId int);

insert into db_task (id, type, name, requestId)VALUES (1, 'HttpRequest', '百度搜索Albert', 1);

insert into db_task (id, type, name, requestId)VALUES (2, 'HttpRequest', '博客园首页', 2);

insert into db_task (id, type, name, requestId, preTaskId, inputEventProperty)VALUES (3, 'StringParser',
                                                                                      '解析博客园首页', 3, 2, 'body');

insert into db_task (id, type, name, requestId, preTaskId, inputEventProperty)VALUES (4, 'RssGenerate',
                                                                                      '生成博客园rss', 4, 3, 'body');

insert into db_task (id, type, name, requestId, preTaskId, inputEventProperty) VALUES (5, 'RegisterForWebPage',
                                                                                       '对外暴露rss访问接口', 5, 4, 'body');

