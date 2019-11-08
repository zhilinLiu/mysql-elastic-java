package com.es.MysqlReader;

import java.util.List;

/**
 *  Mysql数据读取器
 */
public interface MysqlReader {
    //从mysql读取数据
    public List ReadDataFromMysql();
    //送到数据中心
    public boolean sendDataCenter();
}
