CREATE SEQUENCE seq_list
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

create table todo_list
(
	id int default nextval('seq_list'::regclass) not null,
	name varchar,
	status varchar(1),
	description varchar,
	user_id  int ,
	cdate timestamp default LOCALTIMESTAMP,
	udate timestamp default LOCALTIMESTAMP
);

create unique index TodoList_id_uindex
	on todo_list (id);

alter table todo_list
	add constraint TodoList_pk
		primary key (id);

alter table todo_list
	add user_id int;

comment on column todo_list.user_id is 'Fk of User table';

alter table todo_list
	add constraint todo_list_User_fk
		foreign key (user_id) references "user";


CREATE SEQUENCE seq_list_item
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

create table list_item
(
	id int default nextval('seq_list_item'::regclass) not null,
	name varchar,
	description varchar,
	list_id int
		constraint list_id_fk
			references todo_list,
	status varchar(1),
	cdate timestamp default localtimestamp,
	udate timestamp default localtimestamp
);

create unique index list_item_id_uindex
	on list_item (id);

alter table list_item
	add constraint list_item_pk
		primary key (id);


CREATE SEQUENCE seq_user
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;


create table td_user
(
	id int default  nextval('seq_user'::regclass) not null,
	name varchar,
	status varchar(1),
	cdate timestamp default localtimestamp
);

create unique index user_id_uindex
	on td_user (id);

alter table td_user
	add constraint user_pk
		primary key (id);

