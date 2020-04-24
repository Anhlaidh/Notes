# MySQL

##准备

### SQL概述

- Structured Query Language
    - 用来跟数据库打交道,完成和数据库的通信,90%以上的SQL语句是通用的
- 数据库(DB)
    - DB: 通常是一个或一组文件,保存了一些符合特定规格的数据
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
- 简单查询
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