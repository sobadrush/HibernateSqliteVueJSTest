
----------------------------------------------------
-- SELECT * FROM z40180_deptTB;
-- SELECT * FROM z40180_empTB;
----------------------------------------------------
-- DROP TABLE z40180_empTB;
-- DROP TABLE z40180_deptTB;
----------------------------------------------------

--------------------------------------
-- Hibernate 會根據VO的設定建立表格 --
--------------------------------------

--CREATE TABLE z40180_deptTB
--(
--    deptno	int IDENTITY(10,10) PRIMARY KEY,
--	dname	varchar(14),
--	loc		varchar(13) , 
--	[version]   int 
--);
--
--CREATE TABLE z40180_empTB
--(
--    empno	 int IDENTITY(7001,1) PRIMARY KEY,
--	ename	 varchar(14),
--	job		 varchar(13),
--	hiredate  date,		
--	deptno int not NULL,
--	[version]   int,
--	FOREIGN KEY(deptno) REFERENCES z40180_deptTB(deptno)
--	--Hibernate 做關聯查詢其實可以不用在實體表格設定foreign-key
--);

insert into z40180_deptTB( dname , loc , [version] ) values ( '財務部','臺灣台北' , 0);
insert into z40180_deptTB( dname , loc , [version] ) values ( '研發部','臺灣新竹' , 0);
insert into z40180_deptTB( dname , loc , [version] ) values ( '業務部','美國紐約' , 0);
insert into z40180_deptTB( dname , loc , [version] ) values ( '生管部','中國上海' , 0);

insert into z40180_empTB(ename,job,hiredate,deptno,[version]) values ('king','president','1981-11-17' , 1 , 0);
insert into z40180_empTB(ename,job,hiredate,deptno,[version]) values ('blake','manager','1981-05-01'  , 3 , 0);
insert into z40180_empTB(ename,job,hiredate,deptno,[version]) values ('clark','manager','1981-01-09'  , 1 , 0);
insert into z40180_empTB(ename,job,hiredate,deptno,[version]) values ('jones','manager','1981-04-02'  , 2 , 0);
insert into z40180_empTB(ename,job,hiredate,deptno,[version]) values ('martin','salesman','1981-09-28', 4 , 0);
insert into z40180_empTB(ename,job,hiredate,deptno,[version]) values ('allen','salesman','1981-02-2'  , 3 , 0);
insert into z40180_empTB(ename,job,hiredate,deptno,[version]) values ('turner','salesman','1981-09-28', 3 , 0);
insert into z40180_empTB(ename,job,hiredate,deptno,[version]) values ('james','clerk','1981-12-03'    , 3 , 0);
insert into z40180_empTB(ename,job,hiredate,deptno,[version]) values ('ward','salesman','1981-02-22'  , 3 , 0);
insert into z40180_empTB(ename,job,hiredate,deptno,[version]) values ('ford','analyst','1981-12-03'   , 2 , 0);
insert into z40180_empTB(ename,job,hiredate,deptno,[version]) values ('smith','clerk','1980-12-17'    , 2 , 0);
insert into z40180_empTB(ename,job,hiredate,deptno,[version]) values ('scott','analyst','1981-12-09'  , 4 , 0);
insert into z40180_empTB(ename,job,hiredate,deptno,[version]) values ('adams','clerk','1983-01-12'    , 2 , 0);
insert into z40180_empTB(ename,job,hiredate,deptno,[version]) values ('miller','clerk','1982-01-23'   , 1 , 0);

