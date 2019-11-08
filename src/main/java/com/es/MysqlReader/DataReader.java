package com.es.MysqlReader;

import com.es.Exception.DataReaderException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

public class DataReader {
    private ConfigReader config;
    private String url;
    private String username;
    private String password;

    public DataReader(ConfigReader config) {
        this.config = config;
        initialize(config);
    }
    //初始化
    private void initialize(ConfigReader config) {
        try{
            Map configMap = config.getConfigMap();
            this.url = (String) configMap.get("url");
            this.username = (String) configMap.get("username");
            this.password = (String) configMap.get("password");
        }catch (Exception e){
            e.printStackTrace();
            throw new DataReaderException("initialize DataReader failed.jdbcinfo is wrong, check your url or password");
        }
    }

    public Connection getJDBCConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        }catch (Exception e){
            e.printStackTrace();
            throw new DataReaderException("initialize DataReader failed.jdbcinfo is wrong, check your jdbcurl or password");
        }
    }
}
