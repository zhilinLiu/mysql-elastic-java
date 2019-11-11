package com.es.DataTransferCenter;

import com.es.client.ElasticClient;

import java.util.Map;

/**
 *  数据中心
 */
public interface DataCenter {
    //从mysqlReader得到数据
    public void getMyasqlData();

    //得到es客户端
    public void getESClient();

    //操作数据进入ES
    public boolean intoES(ElasticClient client, Map mysqlData, Map indexMap);
}
