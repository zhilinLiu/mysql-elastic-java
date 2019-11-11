# mysql-elastic-java
a simple data transfer application

this is a very simple app.
1.   config XML
2.   start Application
done

You can follow the XML template.

-------------------------------------------------------------
第一步，照着我的XML进行配置
第二步，启动应用等待完成


如果想自定义一些配置，可以自己重写.目前仅支持从mysql数据库导入elastic中.
程序主要有三部分构成:
1.  mysql配置数据读取器
2.  elastic配置读取器
3.  操作中心
操作中心初始化后会初始化mysql读取器和elastic配置读取器，继承DataCenterAbstract并且重写intoES()方法可以自定义怎样操作es.
