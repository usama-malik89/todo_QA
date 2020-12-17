drop table if exists `item` CASCADE 
drop table if exists `todo_list` CASCADE

create table `item` (id bigint PRIMARY KEY AUTO_INCREMENT, is_done boolean not null, `name` varchar(255) not null, priority boolean not null, list_id bigint, primary key (id))
create table todo_list (id bigint PRIMARY KEY AUTO_INCREMENT, colour varchar(255) not null, `name` varchar(255) not null, primary key (id))