drop table if exists princess;
drop table if exists order_records;

create table if not exists princess(id int, name VARCHAR(50), image_src varchar(30), trait varchar(50),price float, qty int);
create table if not exists order_records(id int, qty_ordered int, cust_email varchar(50), cust_name varchar(50), cust_phone int(8));

insert into princess values(1001, 'Snow White', 'snowWhite.jpg', 'pale',11.11, 11);
insert into princess values(1002, 'Sleeping Beauty','sleepingBeauty.jpg','quiet', 22.22, 22);
insert into princess values(1003, 'Cinderella', 'Cinderella.jpg','obeying',33.33, 33);
insert into princess values(1004, 'Rapunzel', 'Rapunzel.jpg','long ass hair',44.44, 44);
insert into princess values(1005, 'The Little Mermaid', 'Ariel.jpg','mermaid',55.55, 55);
insert into princess values(1006, 'Bell', 'Bell.jpg','beautiful',66.66, 66);
insert into princess values(1007, 'Pocahontas', 'Pocahontas.jpg','very original',77.77, 77);
insert into princess values(1008, 'Mulan', 'Mulan.jpg','Chinese',88.88, 88);

select * from princess;
select * from order_records;