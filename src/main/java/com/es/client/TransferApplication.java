package com.es.client;

import com.es.DataTransferCenter.DataCenter;
import com.es.DataTransferCenter.DefaultDataCenter;

/**
 * mysql-es启动类
 * 1.初始化mysqlReader
 * 2.初始化数据中心
 * 3.初始化es操作器
 */
public class TransferApplication {
    public static void start(){
        DataCenter dataCenter = new DefaultDataCenter();
    }
}
