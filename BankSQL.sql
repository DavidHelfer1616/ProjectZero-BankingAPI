
drop table accounts;
drop table clients;

create table clients (
	id serial primary key,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	address VARCHAR(50),
	username VARCHAR(50),
	password VARCHAR(50)
);

create table accounts (
	acc_num serial,
	client_id int,
	balance decimal(10, 2),
	foreign KEY (client_id) references clients(id)
);

insert into clients values
(default, 'Macho', 'Man', 'South', 'username69', 'password'),
(default, 'Miss Macho', 'Lady', 'North', 'username420', 'password69');


insert into accounts values
(default, 1, 500),
(default, 1, 1000),
(default, 2, 5000);


select * from clients c ;
select * from accounts a ;


