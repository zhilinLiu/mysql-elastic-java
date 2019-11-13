package com.es.DataTransferCenter;

import com.es.ElasticReader.StartElastic;
import com.es.client.ElasticClient;
import com.es.reader.MysqlReader.Reader;

import java.util.List;
import java.util.Map;

/**
 *
 */
public abstract class DataCenterAbstract implements DataCenter{
    private Map<String,List> data ;
    private Map<String,String> indexMap;
    private ElasticClient client;
    public DataCenterAbstract(){
        getMyasqlData();
        getESClient();
        if(invoke()){
            System.out.println("数据转移 ----------------------------> 结束");
        }else {
            System.out.println("数据转移 ---------------------------> 失败 : 请检检查原因");
        }
    }
    @Override
    public void getMyasqlData() {
        Reader reader = new Reader();
        Map data = reader.getData();
        this.data = data;
        System.out.println("获取mysql数据 -------------------------------> 成功");
    }

    @Override
    public void getESClient() {
        StartElastic startElastic = new StartElastic();
        Map indexMap = startElastic.getIndexMap();
        this.indexMap = indexMap;
        this.client = startElastic.getClient();
    }
    public boolean invoke(){
        boolean b = intoES(client, data, indexMap);
        return b;
    }
    @Override
    public abstract boolean intoES(ElasticClient client, Map<String, List> mysqlData, Map<String,String> indexMap) ;
}
