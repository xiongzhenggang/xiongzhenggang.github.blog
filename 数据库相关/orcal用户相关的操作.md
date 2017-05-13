## orcale 操作相关

创建Oracle用户权限表开始谈起，然后讲解登陆等一般性动作，使大家对Oracle用户权限表有个深入的了解。

### 一、创建

　　sys;//系统管理员，拥有最高权限

　　system;//本地管理员，次高权限

　　scott;//普通用户，密码默认为tiger,默认未解锁

### 二、登陆

　　sqlplus / as sysdba;//登陆sys帐户

　　sqlplus sys as sysdba;//同上

　　sqlplus scott/tiger;//登陆普通用户scott

### 三、管理用户

　　CREATE USER LOGISTICS IDENTIFIED BY LOGISTICS;

　　alert user scott identified by tiger;//修改密码

### 四，授予权限

1. 默认的普通用户scott默认未解锁，不能进行那个使用，新建的用户也没有任何权限，必须授予权限

```sql
　　grant create session to zhangsan;//授予zhangsan用户创建session的权限，即登陆权限

　　grant unlimited tablespace to zhangsan;//授予zhangsan用户使用表空间的权限

　　grant create table to zhangsan;//授予创建表的权限

　　grant insert table to zhangsan;//插入表的权限

　　grant update table to zhangsan;//修改表的权限

　　grant all to public;//这条比较重要，授予所有权限(all)给所有用户(public)

```

2. oralce对权限管理比较严谨，普通用户之间也是默认不能互相访问的，需要互相授权

```sql
　　grant select on tablename to zhangsan;//授予zhangsan用户查看指定表的权限

　　grant drop on tablename to zhangsan;//授予删除表的权限


　 //授予修改表的权限
　　grant insert(id) on tablename to zhangsan;

　　grant alert all table to zhangsan;//授予zhangsan用户alert任意表的权限

```
### 五、撤销权限

　　基本语法同grant,关键字为revoke


###六、查看权限
```sql

　　select * from user_sys_privs;//查看当前用户所有权限

　　select * from user_tab_privs;//查看所用用户对表的权限

```

###七、操作表的用户的表
```sql
　　select * from zhangsan.tablename

```
###八、权限传递

　　即用户A将权限授予B，B可以将操作的权限再授予C，命令如下：

```sql
grant alert table on tablename to zhangsan with admin option;//关键字 with admin option

　　grant alert table on tablename to zhangsan with grant option;//关键字 with grant option效果和admin类似

```
### 九、角色

　　角色即权限的集合，可以把一个角色授予给用户

```sql

　　create role myrole;//创建角色

　　grant create session to myrole;//将创建session的权限授予myrole

　　grant myrole to zhangsan;//授予zhangsan用户myrole的角色

　　drop role myrole;删除角色

```
### 十、cmd中的操作注意事项
```sql

SQL> set echo on—————————————————设置运行命令是是否显示语句

SQL> set feedback on———————————————-设置显示“已选择XX行”

SQL> set colsep | —————————————————设置列与列之间的分割符号

SQL> set pagesize 10———————————————–设置每一页的行数

SQL> SET SERVEROUTPUT ON——————————-设置允许显示输出类似dbms_output.putline

SQL> set heading on————————————————设置显示列名

SQL> set timing on————————————————–设置显示“已用时间：XXXX”

SQL> set time on—————————————————–设置显示当前时间

SQL> set autotrace on———————————————–设置允许对执行的sql进行分析

```
* 执行一个SQL脚本
```sql
    SQL> start D:\a.sql

    SQL> @ D:\a.sql

```
 * 注：若需要在一个脚本中调用另一个脚本，则使用 @@D:\a.sql

 重新执行上一次命令：
```sql

    SQL> /               --也可使用 run/r 来替代执行
 ```

*  编辑脚本：

 ```sql
    SQL> edit            --编辑当前输入的文本(前1次执行的部分)

    SQL> edit D:\a.sql   --编辑制定文档

    注：在txt中编辑完成后保存，关闭即修改完成，使用“/”后运行

    【6】、保存脚本：

    SQL> save a          --自动存档为a.sql 保存在C:\Documents and Settings\wangxiaoqi

    SQL> save D:\a       --保存到D:\a.sql


    【7】、导入脚本：

    SQL> get D:\a

    【8】、显示一个表结构：

    SQL> desc tab        --如果tab不是表，则只显示其类型和名字

    【9】、保存所有输入：

    SQL> spool D:\xxx    --建立一个xxx.LST文件

    SQL> spool             --显示当前spool状态

    SQL> select * from dual;

    SQL> spool off       --结束录入

  
    【10】、执行过程

    SQL> execute  a;

    【11】、增加页眉和页脚：

    SQL> TTITLE abc      --添加页眉“abc”

    SQL> BTITLE def      --添加页脚“def”

    SQL> TTITLE OFF      --取消页眉显示

【12】

一些常用的小操作：

    show user                      --查看当前登录用户

    show errors                    --显示错误信息

    show rel[ease]                 --显示版本

    show SGA                       --显示SGA

    set time on                    --前端始终显示时间

    select name from v$database;   --查看当前所在数据库

    select * from v$instance;      --查看所有数据库实例(似乎没什么用)

    select * from V_$PWFILE_USERS; --查看那些用户有SYSDBA/SYSOPER权限

```

### 十一 对表操作

oracle建表，并设主键自增

```sql
/* 

   创建用户表      

   */

create table user_info_T(

   id integer not null ,

   user_id varchar(50),

   user_name varchar(50),

   user_password varchar(50),

   user_sex varchar(20),

   user_age integer ,

   user_address varchar(50),

   user_batch varchar(50),

   constraint user_info_T_pk primary key (id) 

);



/*



   创建自增长序列

*/ 



   drop sequence dectuser_tb_seq; /* 若以存在则删除*/

   create sequence dectuser_tb_seq minvalue 1 maxvalue 99999999

   increment by 1

   start with 1;   

   

/*

创建触发器

*/   

          create or replace trigger dectuser_tb_tri

          before insert on user_info_T     /*触发条件：当向表dectuser执行插入操作时触发此触发器*/

           for each row                        /*对每一行都检测是否触发*/

           begin                                  /*触发器开始*/

                 select dectuser_tb_seq.nextval into :new.id from dual;   /*触发器主题内容，即触发后执行的动作，在此是取得序列dectuser_tb_seq的下一个值插入到表user_info_T中的id字段中*/

          end;

		/

/* 

提交

*/ 

commit

/*

   测试

*/

    insert into user_info_T(user_id,user_name,user_password,User_Sex,User_Age) values ('warren','石头','warren','M',22);/*插入测试值*/

    select * from user_info_T /*查询*/

```
## Oracle权限管理

### 一、权限分类：

* 系统权限：系统规定用户使用数据库的权限。（系统权限是对用户而言)。

* 实体权限：某种权限用户对其它用户的表或视图的存取权限。（是针对表或视图而言的）。

### 二、系统权限管理：


1. 系统权限分类：

DBA: 拥有全部特权，是系统最高权限，只有DBA才可以创建数据库结构。

RESOURCE:拥有Resource权限的用户只可以创建实体，不可以创建数据库结构。

CONNECT:拥有Connect权限的用户只可以登录Oracle，不可以创建实体，不可以创建数据库结构。

对于普通用户：授予connect, resource权限。

对于DBA管理用户：授予connect，resource, dba权限。

2. 系统权限授权命令：

[系统权限只能由DBA用户授出：sys, system(最开始只能是这两个用户)]

授权命令：SQL> grant connect, resource, dba to 用户名1 [,用户名2]…;

[普通用户通过授权可以具有与system相同的用户权限，但永远不能达到与sys用户相同的权限，system用户的权限也可以被回收。]

例：
```sql
SQL> connect system/manager

SQL> Create user user50 identified by user50;

SQL> grant connect, resource to user50;

--查询用户拥有哪里权限：

SQL> select * from dba_role_privs;

SQL> select * from dba_sys_privs;

SQL> select * from role_sys_privs;
```
* 删除用户：SQL> drop user 用户名 cascade;  //加上cascade则将用户连同其创建的东西全部删除

 
3. 系统权限传递：

增加WITH ADMIN OPTION选项，则得到的权限可以传递。
```sql
SQL> grant connect, resorce to user50 with admin option;  //可以传递所获权限。
```
4. 系统权限回收：系统权限只能由DBA用户回收

命令：SQL> Revoke connect, resource from user50;

说明：

1）如果使用WITH ADMIN OPTION为某个用户授予系统权限，那么对于被这个用户授予相同权限的所有用户来说，取消该用户的系统权限并不会级联取消这些用户的相同权限。

2）系统权限无级联，即A授予B权限，B授予C权限，如果A收回B的权限，C的权限不受影响；系统权限可以跨用户回收，即A可以直接收回C用户的权限。


### 三、实体权限管理

1. 实体权限分类：select, update, insert, alter, index, delete, all  //all包括所有权限
```sql
execute  --执行存储过程权限

user01:

SQL> grant select, update, insert on product to user02;

SQL> grant all on product to user02;

user02:

SQL> select * from user01.product;

-- 此时user02查user_tables，不包括user01.product这个表，但如果查all_tables则可以查到，因为他可以访问。

```
2. 将表的操作权限授予全体用户：

```sql
SQL> grant all on product to public;  --public表示是所有的用户，这里的all权限不包括drop。

[实体权限数据字典]:

SQL> select owner, table_name from all_tables; -- 用户可以查询的表

SQL> select table_name from user_tables;  --用户创建的表

SQL> select grantor, table_schema, table_name, privilege from all_tab_privs; -- 获权可以存取的表（被授权的）

SQL> select grantee, owner, table_name, privilege from user_tab_privs;   -- 授出权限的表(授出的权限)
```
3. DBA用户可以操作全体用户的任意基表(无需授权，包括删除)：

  DBA用户：
```sql
SQL> Create table stud02.product(

id number(10),

name varchar2(20));

SQL> drop table stud02.emp;

SQL> create table stud02.employee

as

select * from scott.emp;

```
4. 实体权限传递(with grant option)：

   user01:
```sql
SQL> grant select, update on product to user02 with grant option; // user02得到权限，并可以传递。
```
5. 实体权限回收：

   user01:
``sql
SQL>Revoke select, update on product from user02;  //传递的权限将全部丢失。

```
* 说明

1）如果取消某个用户的对象权限，那么对于这个用户使用WITH GRANT OPTION授予权限的用户来说，同样还会取消这些用户的相同权限，也就是说取消授权时级联的。

Oracle用户管理

### 一、创建用户的Profile文件
```sql
SQL> create profile student limit  // student为资源文件名

FAILED_LOGIN_ATTEMPTS  3  //指定锁定用户的登录失败次数

PASSWORD_LOCK_TIME 5  //指定用户被锁定天数

PASSWORD_LIFE_TIME 30  //指定口令可用天数
```
### 二、创建用户
```sql
SQL> Create User username

Identified by password

Default Tablespace tablespace

Temporary Tablespace tablespace

Profile profile

Quota integer/unlimited on tablespace;
```

* 例:
```sql
SQL> Create user acc01

identified by acc01   // 如果密码是数字，请用双引号括起来

default tablespace account

temporary tablespace temp

profile default

quota 50m on account;

SQL> grant connect, resource to acc01;

-- [*] 查询用户缺省表空间、临时表空间

SQL> select username, default_tablespace, temporary_tablespace from dba_users;

[*] 查询系统资源文件名：

SQL> select * from dba_profiles;

-- 资源文件类似表，一旦创建就会保存在数据库中。

SQL> select username, profile, default_tablespace, temporary_tablespace from dba_users;

SQL> create profile common limit

failed_login_attempts 5

idle_time 5;

SQL> Alter user acc01 profile common;

```

### 三、修改用户：

```sql
SQL> Alter User 用户名

Identified 口令

Default Tablespace tablespace

Temporary Tablespace tablespace

Profile profile

Quota integer/unlimited on tablespace;

```

1. 修改口令字：

SQL>Alter user acc01 identified by “12345″;

2. 修改用户缺省表空间：

SQL> Alter user acc01 default tablespace users;

3. 修改用户临时表空间

SQL> Alter user acc01 temporary tablespace temp_data;

4. 强制用户修改口令字：

SQL> Alter user acc01 password expire;

5. 将用户加锁

SQL> Alter user acc01 account lock;  // 加锁

SQL> Alter user acc01 account unlock;  // 解锁


### 四、删除用户
```sql
SQL>drop user 用户名;  //用户没有建任何实体

SQL> drop user 用户名 CASCADE;  // 将用户及其所建实体全部删除

```
*  当前正连接的用户不得删除。

### 五、监视用户：

1. 查询用户会话信息：

SQL> select username, sid, serial#, machine from v$session;

2. 删除用户会话信息：

SQL> Alter system kill session ‘sid, serial#’;

3. 查询用户SQL语句：

SQL> select user_name, sql_text from v$open_cursor;


## Oracle角色管理

### 一、何为角色

角色是一组权限的集合，将角色赋给一个用户，这个用户就拥有了这个角色中的所有权限。

### 二、系统预定义角色


预定义角色是在数据库安装后，系统自动创建的一些常用的角色。下介简单的介绍一下这些预定角色。角色所包含的权限可以用以下语句查询：

sql>select * from role_sys_privs where role=’角色名’;


1. CONNECT, RESOURCE, DBA

这些预定义角色主要是为了向后兼容。其主要是用于数据库管理。oracle建议用户自己设计数据库管理和安全的权限规划，而不要简单的使用这些预定角色。将来的版本中这些角色可能不会作为预定义角色。

2. DELETE_CATALOG_ROLE， EXECUTE_CATALOG_ROLE， SELECT_CATALOG_ROLE

这些角色主要用于访问数据字典视图和包。

3. EXP_FULL_DATABASE， IMP_FULL_DATABASE

这两个角色用于数据导入导出工具的使用。

4. AQ_USER_ROLE， AQ_ADMINISTRATOR_ROLE

AQ:Advanced Query。这两个角色用于oracle高级查询功能。

5.  SNMPAGENT

用于oracle enterprise manager和Intelligent Agent

6. RECOVERY_CATALOG_OWNER

用于创建拥有恢复库的用户。关于恢复库的信息，参考oracle文档《Oracle9i User-Managed Backup and Recovery Guide》

7. HS_ADMIN_ROLE

A DBA using Oracle’s heterogeneous services feature needs this role to access appropriate tables in the data dictionary.


### 三、管理角色


1. 建一个角色

sql>create role role1;

2. 授权给角色

sql>grant create any table,create procedure to role1;

3. 授予角色给用户

sql>grant role1 to user1;

4. 查看角色所包含的权限

sql>select * from role_sys_privs;

5. 创建带有口令以角色(在生效带有口令的角色时必须提供口令)

sql>create role role1 identified by password1;

6. 修改角色：是否需要口令

sql>alter role role1 not identified;

sql>alter role role1 identified by password1;

7. 设置当前用户要生效的角色

(注：角色的生效是一个什么概念呢？假设用户a有b1,b2,b3三个角色，那么如果b1未生效，则b1所包含的权限对于a来讲是不拥有的，只有角色生效了，角色内的权限才作用于用户，最大可生效角色数由参数MAX_ENABLED_ROLES设定；在用户登录后，oracle将所有直接赋给用户的权限和用户默认角色中的权限赋给用户。）

sql>set role role1;//使role1生效

sql>set role role,role2;//使role1,role2生效

sql>set role role1 identified by password1;//使用带有口令的role1生效

sql>set role all;//使用该用户的所有角色生效

sql>set role none;//设置所有角色失效

sql>set role all except role1;//除role1外的该用户的所有其它角色生效。

sql>select * from SESSION_ROLES;//查看当前用户的生效的角色。



8. 修改指定用户，设置其默认角色

sql>alter user user1 default role role1;

sql>alter user user1 default role all except role1;

详见oracle参考文档

9. 删除角色

sql>drop role role1;

角色删除后，原来拥用该角色的用户就不再拥有该角色了，相应的权限也就没有了。

* 说明:

1)无法使用WITH GRANT OPTION为角色授予对象权限。



2)可以使用WITH ADMIN OPTION 为角色授予系统权限，取消时不是级联。

10：创建表：

1. Oracle中常用的数据类型

1) CHAR[(<size> [<BYTE|CHAR>])]

	 以定长的方式存储字符数据，不足则在右侧补空格

	 设置长度时，如果不指定长度单位，默认为"字节"

	 如果不设置长度，默认为1个"字节"；最大可设置到2000个"字节"

2) VARCHAR2(<size> [<BYTE|CHAR>])

	 以变长的方式存储字符数据，定义时必须设置长度，最长为4000个"字节"

	 设置长度时，如果不指定长度单位，默认为"字节"

3) NUMBER[(p, s)]

	 p，即precision，表示精度，用来指定数字的个数，取值在1~38之间 

	 s，即scale，表示小数位数，取值在-84~127之间

 如果不设置精度，p默认为38，这时不能设置s的值，数字任意分布在小数点两侧

 如果设置了精度，可以不设置s，则s值为0，只能存储整数

 插入的数据不符合精度的要求，是语法错误；不符合s要求会四舍五入

 当s > 0，并且s >= p时，只能存储纯小数，并且该数小数点后面至少要有s-p个0

4) REAL ---> 18位精度的浮点数

5) INTEGER ---> NUMBER(38)

	6) DATE ---> 包含7个属性值：世纪、年、月、日、时、分、秒

//

CREATE TABLE table_name (

column_name type [DEFAULT value]

[, …]

		);

11，向表中插入数据：

INSERT INTO table_name [(column_name[, …])] VALUES (数据[, …]);

比如：

SQL> insert into left values(1,'a');


## 数据库高级应用：

```sql
SQL> select * from left;


        ID NAME

---------- --------------------

         1 a

         2 b



SQL> select * from right;



        ID RNAME

---------- -----------------------

         1 a1

         2 b1

         3 c1

```
1. 内联接（典型的联接运算，使用像 =  或 <> 之类的比较运算符）。包括相等联接和自然联接。     

内联接使用比较运算符根据每个表共有的列的值匹配两个表中的行。例如，检索 students和courses表中学生标识号相同的所有行。   

    
2. 外联接。外联接可以是左向外联接、右向外联接或完整外部联接。     

在 FROM子句中指定外联接时，可以由下列几组关键字中的一组指定：     

1）LEFT  JOIN或LEFT OUTER JOIN， 表连接的标准语法    

左向外联接的结果集包括  LEFT OUTER子句中指定的左表的所有行，而不仅仅是联接列所匹配的行。如果左表的某行在右表中没有匹配行，则在相关联的结果集行中右表的所有选择列表列均为空值。       

比如;

```sql
SQL> select l.name,r.rname from left l,right r where l.id=r.id(+); -- 左外连接，以left表为主

NAME                                     RNAME

---------------------------------------- -----------------------------

a                                        a1

b                                        b1

表连接的标准语法

SQL> select l.name,r.rname from left l left outer join right r on (l.id=r.id);



NAME                                     RNAME

---------------------------------------- ----------------------------------------

a                                        a1

b                                        b1

```
2）RIGHT  JOIN 或 RIGHT  OUTER  JOIN     

右向外联接是左向外联接的反向联接。将返回右表的所有行。如果右表的某行在左表中没有匹配行，则将为左表返回空值。 
```sql
SQL> select l.name,r.rname from left l,right r where l.id(+)=r.id;-- 右外连接，以right表为主，所以left的name列的最后一行为null；

NAME                                     RNAME

---------------------------------------- ----------------------------

a                                        a1

b                                        b1

                                         c1
```
    
3）FULL  JOIN 或 FULL OUTER JOIN

完整外部联接返回左表和右表中的所有行。当某行在另一个表中没有匹配行时，则另一个表的选择列表列包含空值。如果表之间有匹配行，则整个结果集行包含基表的数据值。   
```sql
SQL> select l.name,r.rname from left l full outer join right r on (l.id=r.id);



NAME                                     RNAME

---------------------------------------- ---------------------------------------

a                                        a1

b                                        b1

                                         c1
```
3. 交叉联接   

交叉联接返回左表中的所有行，左表中的每一行与右表中的所有行组合。交叉联接也称作笛卡尔积。    

FROM 子句中的表或视图可通过内联接或完整外部联接按任意顺序指定；但是，用左或右向外联接指定表或视图时，表或视图的顺序很重要。有关使用左或右向外联接排列表的更多信息，请参见使用外联接。 

组函数：

1. count函数

2.  GROUP BY子句? 只有参与分组的列，才可以单独在SELECT子句中使用


### 关于orcale中回滚操作记录

1. 没有提交(commit)的数据删除后无法rollback

2. 提交(commit)了的数据删除后可以使用rollback恢复

3. 删除数据后提交(commit)则无法使用rollback恢复

理解上述的三种条件，其基本原理就是在没有commit时数据是暂存在缓冲区缓存中还没有写入内存中，commit则是将缓存的数据写入内存。这就会导致

在删除后进行commit操作则会导致无法回退。

就是说，你提交了的数据是永久性的，rollback是在这个基础上进行备份的。

具体操作有表connectA

```sql
SQL> select * from connectA ;



        ID NAME                                            AGE HOBBY

---------- ---------------------------------------- ---------- ----------------------------------------

         1 张三                                             12 swimming

         2 李四                                             18 drving

         3 王五                                             20 playgame

         4 xiaoli                                           15 havefun



SQL> insert into connectA(id,name,age,hobby) values(5,'xx',22,'hhhh');



已创建 1 行。



SQL> commit;



提交完成。



SQL> savepoint save1;



保存点已创建。



SQL> delete connectA where id = 5;



已删除 1 行。

SQL> select * from connectA;



        ID NAME                                            AGE HOBBY

---------- ---------------------------------------- ---------- ----------------------------------------

         1 张三                                             12 swimming

         2 李四                                             18 drving

         3 王五                                             20 playgame

         4 xiaoli                                           15 havefun

SQL> rollback to savepoint save1;



回退已完成。



SQL> select * from connectA;



        ID NAME                                            AGE HOBBY

---------- ---------------------------------------- ---------- ----------------------------------------

         1 张三                                             12 swimming

         2 李四                                             18 drving

         3 王五                                             20 playgame

         4 xiaoli                                           15 havefun

         5 xx                                               22 hhhh
```
