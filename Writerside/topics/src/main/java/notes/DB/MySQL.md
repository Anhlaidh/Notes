# Table of Contents

* [MySQL](#mysql.)
      * [](#.)
    * [SQL概述](#sql概述.)
    * [连接数据库](#连接数据库.)
    * [概念](#概念.)
  * [常用命令](#常用命令.)
    * [Base](#base.)
      * [简单查询](#简单查询.)
      * [分组查询](#分组查询.)
      * [总结](#总结.)
      * [连接查询](#连接查询.)
      * [子查询](#子查询.)
      * [union](#union.)


# MySQL
## 介绍 
### 数据是什么
### 如何存储
### 数据库引擎
- innodb
- myisam
- memory

- 区别
    1. innodb存储引擎数据跟索引文件放在一个文件中,myisam分为不同文件
    2. innodb支持事务,myisam不支持事务
    3. innodb支持表锁和行锁,myisam支持表锁
    4. innodb有外键,myisam没有外键
### 索引的分类
- 主键索引
    - 主键索引是一种唯一性索引,但它必须指定为primary key,每个表只能有一个主见
- 唯一索引
    - 索引的列所有值都只能出现一次,即必须唯一,值可以为空
- 普通索引
    - 基本的索引类型,值可以为空,没有唯一性限制
- 全文索引
    - 全文索引类型为FULLTEXT,全文索引可以在varchar,char,text类型的列上创建
- 组合索引
    - 多列值组成一个索引,专门用于组合搜索
    
#### 回表
#### 最左匹配原则
#### 索引覆盖
#### 索引下推
`select * from t1 where age=? and gender=?`
先根据age字段从存储引擎层将数据拉取回server层,然后在server层进行gender筛选,再从存储引擎层拉去数据的
时候,就会直接根据age,gender一起筛选
##准备
### 数据库
- 云数据库
    - RDS,PolarDB,TDSQL
- NewSQL
    - TiDB
- 生产线
    - 厂家
        - Oracle 官方
        - MariaDB
        - Percona
- 5.6
    - 5.6.36,5.6.38,5.6.40,5.6.46
- 5.7
    - 5.7.20 22 24....
- 8.0
    - 8.0.11,8.0.17,8.0.18
    
- C:社区版
- E:企业版
### linux环境 mysql指令
- 数据初始化
    - `mysqld --initialize-insecure --user=xxx --basedir=/app/database/mysql --datadir=/data/3306/`
    - 有-insecure
        1. 初始化完成后,有12位临时密码,必须在使用mysql之前重置这个密码
        2. 密码管理使用严格模式: 3种密码复杂度,8位以上
        3. 
### SQL概述

- Structured Query Language
    - 用来跟数据库打交道,完成和数据库的通信,90%以上的SQL语句是通用的
- 数据库(notes.DB)
    - notes.DB: 通常是一个或一组文件,保存了一些符合特定规格的数据
    - DBMS:DataBase Management System 数据库管理系统 MySql,Oracle,执行SQL,操作数据库
    
### 连接数据库

- mysql -uxxxxx -pxxxxx
```shell script
msyql -uroot -p123456
```

### 概念

- table 
    - 一种结构化的文件,用来存储特定类型的数据
- SQL的分类
    - 数据查询语言(DQL-Data Query Language)
        - select
    - 数据操纵语言(DML-Data Manipulation Language)
        - insert,delete,update
    - 数据定义语言(DDL-Data Definition Language)
        - create,drop,alter
    - 事务控制语言(TCL-Transactional Control Language)
        - commit,rollback
    - 数据控制语言(DCL-Data Control Language)
        - grant,revoke

## 常用命令

- `select version();`查看版本
- `select database();`查看当前数据库
- `desc <database>;` 查看详细信息
- `show create table <tablename>;`查看建表语句

### Base
 
#### 简单查询
- 查询
    - `select <volumes> from <table>;`  查询某个字段的信息(无视大小写)
        - 可以加数学表达式
            - `select empno,ename,sal*12 from emp;`
        - 重命名
            - ` select empno,ename,sal*12 as years from emp;` 加as,可空格不写as select empno,ename,sal*12 as years from emp;
- 条件查询
    - `select <volumes> from <table> where <条件>;`
    - 条件
        -   | 运算符 | 说明 |  
            | --- | --- |  
            | = | 等于|
            | <>或!= |不等于 |
            | < | 小于|
            | > | 大于|
            | >=|大于等于 |
            | between ...and...| 两只之间,等同于>= and <=,左闭右开 |
            | is null| 为null(is not null 不为空) |
            | and| 并且 ,优先级高于'or' |
            | or| 或者 |
            | in| 包含 |
            | not| 取非,主要用在is 或 in中|
            | like| 模糊查询,支持%或下划线匹配,%匹配任意个字符,下划线,一个下划线匹配一个字符

- 排序
    - `order by <volume>` 默认升序,volume可为数字,代表第几个属性
        - `asc` 升序
        - `desc` 降序
        
- 数据处理函数/单行处理函数
    - 运算符|说明| 示例
       --- | --- |---
      Lower| 转换小写|lower(ename)
      upper|转换大写|upper(ename)
      substr|子串|substr(ename,1,2)
      length|取长度|length(ename)
      trim|去空格|trim(ename)
      str_to_date|将字符串换成日期|str_to_date('字符串','时间格式')
      date_format|格式化日期|date_format(日期类型数据,'日期格式')
      format|设置千分位|
      round|四舍五入|round(comm,0)第二个参数为取得为数,可为负数|
      rand()|生成随机数|与java Math.Random()类似
      ifnull|将null转换为具体值|ifnull(comm,0)如果comm为null,则转换为0

- 日期
    - 关于MySQL中的日期处理
        - 每一个数据库处理日期的时候,采用的机制都是不同的,日期处理都有自己的一套机制,所以在实际开发中
        表中的字段定义为DATE类型,这种情况很少,因为一旦使用日期类型,那么java程序将不能够通用,在实际开发中
        一般使用"日期字符串"来表示日期
- MySQL数据库管理系统中对日期的处理提供了两个重要函数
    - str_to_date
    - date_format
- str_to_date
    - 该函数的作用是:将日期字符串转换成日期类型 varchar-->date
    - 该函数的执行结果是DATE类型
    - 该函数的使用格式
        - str_to_date('日期字符串','日期格式')
```sql 
select ename,hiredate from emp where hiredate=str_to_date('12-17-1980','%m-%d-%Y');
```

- date_format
    - 该函数的作用是:将日期类型date转换成剧有特定格式的日期字符串varchar  date-->varchar
    - 该函数的运算结果是:varchar类型(具备特定格式)
    - 该函数的语法格式:
        - date_format(日期类型数据,'日期格式')
```sql
 select ename,date_format(hiredate,'%m-%d-%Y') as hiredate from emp;
```
    
- 日期格式
    - notes.Java|MySQL|说明
       ---|---|---
       yyyy|%Y|年
       MM|%m|月
       dd|%d|日
       HH|%H|时
       mm|%i|分
       ss|%s|秒
       SSS| |毫秒 
         

- 分组函数/聚合函数/多行处理函数 (忽略空值)
    - 函数|说明
      ---|---
      count|取得记录值
      sum|求和
      avg|取平均
      max|取最大值
      min|取最小
    - 分组函数不能直接用在where函数中
    
- distinct 去重复
    - 前面不能有字段

```sql
select distinct job from emp;
```

#### 分组查询

- 分组查询涉及的两个子句:
    - group by 分组,优先执行(先分组,再where,再select再having)
    - having 分组之后过滤
- group by
    - 若一条DQL语句中有group by子句,那么select关键字后面只能跟参与分组的字段和分组函数
求不同job的最大sal
```sql
 select max(sal) from emp group by job;
```
求不同job的最大sal也显示job
```sql
select job,max(sal) from emp group by job;
```
两个字段联合划分分组
```sql
 select deptno,job,max(sal) from emp group by job,deptno;
```
select: 分组前筛选
having: 分组后筛选
```sql
 select job,avg(sal) from emp group by job having avg(sal)>1500;

```
- 原则:
    - 尽量在where中过滤,无法过滤的数据,通常需要先分组之后再过滤,这个时候可以选择用having.效率问题
    
#### 总结

```sql
select
    ...
from
    ...
where
    ...
group by
    ...
having
    ...
order by
    ...

```
1. 顺序不能变
2. 执行顺序
    1. from 从某张表中查询出来
    2. where 经过某条件进行过滤
    3. group by 然后分组
    4. having 分组之后不满意再过滤
    5. select 查询出来
    6. order by 排序输出
    
#### 连接查询

1.  什么是连接查询
    - 查询的时候只从一张表检索数据,称为单表查询
    - 在实际开发中,数据并不是存储在一张表中,是同时存储在多张表中,这些表和表之箭存在关系,我们在检索的时候通常是
    需要将多张表联合起来取得有效数据,这种多表查询被称为连接查询或者叫做跨表查询
2. 链接查询根据出现年代分类
    - SQL92[1992]
    - SQL00[1999:更新的语法,主要掌握这种]
    
3. 连接查询根据连接方式可分为
    - 内连接 :A表和B表能够完全匹配的记录查询出来,被称为内连接(inner可以省略)
        - 等值连接  
            - SQL92语法:等值连接
            ```sql
              select e.ename ,d.dname from emp e ,dept d where e.deptno=d.deptno;
            ```
            -SQL99语法:等值连接 join on
                - 表独立出来了,结构更清晰,对表连接不满意的话可以追加where过滤
             ```sql
              select e.ename ,d.dname from emp e join dept d on e.deptno=d.deptno;
             ```

        - 非等值连接
            - SQL99:
                 ```sql
                 select e.ename,s.grade from emp e , salgrade s where e.sal between s.losal and hisal;
                 ```
            - SQL99:
                ```sql
                select e.ename,s.grade from emp e join salgrade s on e.sal between s.losal and hisal;
                ```
        - 自连接
            - SQL99:
                ```sql
                select e.ename , m.ename from emp e , emp m where m.empno=e.mgr;
                ``` 
            - SQL99:
                ```sql
                select e.ename , m.ename from emp e join emp m on m.empno=e.mgr;
                ``` 
    - 外连接 : (outer可以省略)
        - A表和B表能够完全匹配的记录查询出来之外,将其中一张表的记录无条件的完全查询出来.
    对方没有匹配的记录,会自动模拟出null与之匹配,这种查询被称为外连接
        - 外连接的查询结果条数>=内连接的查询结果条数
    
        - 左外连接
            - SQL99:
                ```sql
                select e.ename,d.dname from dept d left join emp e on e.deptno=d.deptno;
                ``` 
        - 右外连接
            - SQL99:
                ```sql
                 select e.ename,d.dname from emp e right join dept d on e.deptno=d.deptno;
                ``` 
        - 任何一个右外连接都可以写成左外连接,反之亦可
    - 全连接
    
- 若两张表进行连接查询的时候没有任何条件限制,最终的查询结果总数是两张表记录的条数的乘积,这种现象被称为
笛卡尔积现象,为了避免笛卡尔积现象的发生,必须在进行表连接的时候添加限制条件
```sql
 select e.ename ,d.dname from emp e ,dept d;
```
- 多张表连接的语法格式
```sql
select
    xxx
from
    a
join
    b
on
    条件
join
    c
on
    条件;

```
原理:a和b进行表连接之后,a再和c进行表连接

-  案例:找出每一个员工对应的部门名称,以及该员工的工资等级:
```sql
 select e.ename ,d.dname,s.grade from emp e join dept d on e.deptno=d.deptno join salgrade s on e.sal between losal and hisal;
```


子查询:
```sql
select e.ename,e.dname,s.grade from (select e.ename,e.sal,d.dname from emp e left join dept d on e.deptno=d.deptno) e left join salgrade s on e.sal between s.losal and hisal;
```
#### 子查询

1. 什么是子查询
    - select语句嵌套select语句
2. 子查询可以出现在哪儿
    - select...(select)
    - from....(select)
    - where....(select)
    
select后select
```sql
select e.ename,(select d.dname from dept d where e.deptno=d.deptno)as dname from emp e;
```
- 找出每个部门的平均薪水,并且要求显示平均薪水的薪水等级
from 后select
```sql
select d.dname,s.grade from( select d.dname ,e.sal from (select deptno,avg(sal) as sal from emp group by deptno) e join dept d on d.deptno=e.deptno) d join salgrade s on d.sal between losal and hisal;
```

#### union

- 连接两个表一同输出
```sql
select xxxxx
union
select xxxxx
```
字段个数相同,(Oracle中数据类型也要相同)

#### limit
1. 获取一张表中的某部分数据
2. 只在MySQL数据库中存在,不通用,是MySQL数据库的特色
```sql
 select ename ,sal from emp limit 5;
```
limit 5 代表,从表中记录下标0开始,取五条等同于下面的sql语句
```sql
 select ename ,sal from emp limit 0,5;
```
limit的语法: limit 起始下标,长度
    - 起始坐标没有指定,默认从0开始,0表示表中的第一条ji'lu
    
- 找出公司中工资排名在前五的员工
```sql
select ename ,sal from emp order by sal desc limit 5;
```

### 表

- DDL
