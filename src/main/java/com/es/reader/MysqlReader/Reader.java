package com.es.reader.MysqlReader;

import com.es.Exception.DataReaderException;
import org.dom4j.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 读取数据实现类
 */
public class Reader implements MysqlReader {
    private ConfigReader config;
    private Connection conn;
    private Map data;
    public Reader() {
        initializeConfigReader();
        initializeMysqlReader();
    }
    //初始化XML读取器
    private void initializeConfigReader(){
        config = new ConfigReader();
        Document document = config.configRead("elastic-mysql.xml");
        ConfigReader config = this.config.getConfig(document);
        this.config = config;
    }
    //初始化数据库连接池
    private void initializeMysqlReader(){
        DataReader dataReader = new DataReader(config);
        System.out.println("启动数据读取器:    true");
        this.conn = dataReader.getJDBCConnection();
        boolean b = ReadDataFromMysql();
        System.out.println("读取mysql数据:  "+b);
    }

    //这块容易报错
    @Override
    public boolean ReadDataFromMysql() {
        Map sqlMap = config.getSqlMap();
        LinkedHashMap<String, List> map = new LinkedHashMap<>();
        sqlMap.forEach((tableName,value1)->{
            LinkedList<List> rows = new LinkedList<>();
            String  value = (String) value1;
            String sql ="select "+ value+" from "+ tableName;
            String[] feilds = value.split(",");
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                //如果resultSet还有数据
                while(resultSet.next()){
                    LinkedList<Object> row = new LinkedList<>();
                    for (String feild : feilds) {
                        Object object = resultSet.getObject(feild);
                        row.add(object);
                    }
                    rows.add(row);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DataReaderException("从表中读取数据失败");
            }
            map.put(tableName.toString(),rows);
        });
        this.data = map;
        return true;
    }

    @Override
    public Map getData() {
        return this.data;
    }


}
