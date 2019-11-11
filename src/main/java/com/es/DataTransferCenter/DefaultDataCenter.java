package com.es.DataTransferCenter;

import com.es.Exception.CreateIndexException;
import com.es.client.ElasticClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class DefaultDataCenter extends DataCenterAbstract {
    @Override
    public boolean intoES(ElasticClient client, Map<String, List> mysqlData, Map<String,String> indexMap) {
        mysqlData.forEach((tableNameFields,rows)->{
            String[] split = tableNameFields.split(":");
            String tableName = split[0];
            String[] fields = split[1].split(",");
            //ES客户端创建表名
            try {
                //得到indexMap的相关信息
                final String[] indexMSG = new String[1];
                indexMap.keySet().forEach(x->{
                    if (x.contains(tableName)){
                        indexMSG[0] =x;
                    }
                });
                String[] strings = indexMSG[0].split(":");
                String indexTable = strings[0];
                String shards= strings[1];
                String replicas = strings[2];
                try {
                    client.ops().createIndex(tableName,Integer.parseInt(shards),Integer.parseInt(replicas),indexMap.get(indexMSG[0]));
                }catch (Exception e){
                    System.out.println("索引已存在，正在把数据存进去");
                }finally {
                    //把List中的数据放入es中
                    rows.forEach(row->{
                        row = (List) row;
                        HashMap<String, Object> map = new HashMap<>();
                        for(int i=0;i<((List) row).size();i++){
                            map.put(fields[i],((List) row).get(i));
                        }
                        try {
                            client.ops().putDoc(tableName,map);
                        } catch (IOException e) {
                            System.out.println("错误信息 ----------------->  map放入ES中时错误");
                        }
                    });
                }


            } catch (NullPointerException e) {
                e.printStackTrace();
                throw new CreateIndexException("在elastic的配置中没有"+tableName+"这个配置");
            }
        });
        return true;
    }
}
