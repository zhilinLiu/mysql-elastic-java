package com.es.reader.MysqlReader;

import com.es.reader.Read;

import java.util.Map;

/**
 *  Mysql数据读取器
 */
public interface MysqlReader extends Read {
    //从mysql读取数据
    public boolean ReadDataFromMysql();
    //送到数据中心
    public Map getData();
}
