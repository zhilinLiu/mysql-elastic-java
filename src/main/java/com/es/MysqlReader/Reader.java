package com.es.MysqlReader;

import org.dom4j.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * 读取数据实现类
 */
public class Reader implements MysqlReader {
    private ConfigReader config;
    private Connection conn;
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
        this.conn = dataReader.getJDBCConnection();
    }

    //这块容易报错
    @Override
    public List ReadDataFromMysql() {
        Map sqlMap = config.getSqlMap();
        LinkedList<List> rows = new LinkedList<>();
        sqlMap.forEach((tableName,value1)->{
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
            }finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        return rows;
    }

    @Override
    public boolean sendDataCenter() {
        return false;
    }


}
