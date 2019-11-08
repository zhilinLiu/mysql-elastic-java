package com.es.MysqlReader;

import org.dom4j.Document;

import java.util.List;

/**
 * 读取数据实现类
 */
public class Reader implements MysqlReader {
    private ConfigReader config;

    public Reader() {
        initializeConfigReader();
        initializeMysqlReader();
    }
    private void initializeConfigReader(){
        config = new ConfigReader();
        Document document = config.configRead("elastic-mysql.xml");
        ConfigReader config = this.config.getConfig(document);
        this.config = config;
    }

    private void initializeMysqlReader(){

    }

    @Override
    public List ReadDataFromMysql() {
        return null;
    }

    @Override
    public boolean sendDataCenter() {
        return false;
    }

    public ConfigReader getConfig() {
        return config;
    }
}
