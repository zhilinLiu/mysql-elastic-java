# project background
Idle boring write, although there are other mature import tools, but go language and python language, learn configuration is impossible, this life can not, had to write their own play.
# project role
Moving data from mysql to elastic through configuration files has been implemented to import data from mysql to elastic.
** currently cannot handle data type, please use string type ** when setting data type of es
# usage
Step 1: configure the XML
I put a configuration template in the resources directory under the <increment> tag true and false to enable incremental imports.
After opening, the current data increment node will be added under the label after importing the table, saving the current number of data rows.
The rest is configured according to my configuration template.
Step 2: start the program
At startup, run parameters must be added such as:
Java - jar demo. Jar c: / / elastic - mysql. XML
or
Java - jar demo. Jar/home/mysql - elastic - Java/elastic - mysql. XML
### startup complete

-------------------------------------------------------------
# 项目背景
闲着无聊写的，虽然有其他成熟的导入工具，但是go语言和python语言的，学配置是不可能的，这辈子都不能，只好自己写着玩。

# 项目作用
通过配置文件的方式，把mysql里的数据转移到elastic里面，目前已实现从mysql导入数据到elastic.
**目前还不能处理data类型，在设定es的data数据类型时请使用string类型**
# 使用方式
### 第一步：配置xml
我在resources目录下放了一个配置模板<increment>标签下是的ture和false是否开启增量导入。
而开启后导入表后会在该标签下增加当前数据增量节点，保存了当前的数据行数。
其他的按照我的配置模板配置就行了。
### 第二步：启动程序
在启动的时候，必须添加运行参数 例如： 
java -jar demo.jar c://elastic-mysql.xml    
或者 
java -jar demo.jar /home/mysql-elastic-java/elastic-mysql.xml
### 启动完毕

