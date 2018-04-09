drop table CreateStates;
create table CreateStates (StateName varchar(15),Region varchar(15), LargestCity varchar(25), Capital varchar(20), Population int);
insert into CreateStates  values('California', 'Western', 'Los Angeles','Sacramento', 25000000);
insert into CreateStates  values('Michigan', 'Northern', 'Detroit','Lansing', 6000000);
insert into CreateStates  values('Virginia', 'Eastern', 'Fairfax', 'Richmond',9000000);
insert into CreateStates  values('Alabama', 'Southern', 'Mobile','Montgomery', 5000000);
select * from CreateStates;