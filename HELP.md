# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

```java
public class Test{ 
public static void main(String[] args){
  System.out.println(); 
}
}
```

1、安装mysql
docker run --name mysql-master  -e MYSQL_ROOT_PASSWORD=root -v $PWD/conf:/etc/mysql/conf.d -v $PWD/logs:/logs -v $PWD/data:/var/lib/mysql -d -p 3307:3306 mysql
docker run --name mysql-slave0  -e MYSQL_ROOT_PASSWORD=root -v $PWD/conf:/etc/mysql/conf.d -v $PWD/logs:/logs -v $PWD/data:/var/lib/mysql -d -p 3308:3306 mysql
docker run --name mysql-slave1  -e MYSQL_ROOT_PASSWORD=root -v $PWD/conf:/etc/mysql/conf.d -v $PWD/logs:/logs -v $PWD/data:/var/lib/mysql -d -p 3309:3306 mysql
2、配置master
[root@localhost master]# cat conf/mysql.cnf 
[mysqld]
character-set-server = utf8
log-bin = mysql-bin
server-id = 1
sql_mode=STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION

mysql> show master status;
+------------------+----------+--------------+------------------+-------------------+
| File             | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
+------------------+----------+--------------+------------------+-------------------+
| mysql-bin.000007 |      675 |              |                  |                   |
+------------------+----------+--------------+------------------+-------------------+
1 row in set (0.00 sec)

2、配置slave

[root@localhost conf]# cat mysql.cnf 
[client]default-character-set=utf8
[mysql]
default-character-set=utf8
[mysqld]
log-bin=mysql-bin
server-id=2
sql_mode=STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION

mysql>change master to master_host='192.168.0.120',master_port=3307,master_user='root',master_password='root',master_log_file='mysql-bin.000007',master_log_pos=675;

mysql>stop slave;
mysql>set GLOBAL SQL_SLAVE_SKIP_COUNTER=1;
 mysql>start slave;
mysql> show slave status\G
*************************** 1. row ***************************             
  Slave_IO_State: Waiting for master to send event               
     Master_Host: 192.168.0.120                 
      Master_User: root                 
       Master_Port: 3307                
       Connect_Retry: 60              
       Master_Log_File: mysql-bin.000002          
       Read_Master_Log_Pos: 2307               
       Relay_Log_File: e2946b0db5c9-relay-bin.000005                
       Relay_Log_Pos: 1159        
       Relay_Master_Log_File: mysql-bin.000002             
       Slave_IO_Running: Yes            
       Slave_SQL_Running: Yes
