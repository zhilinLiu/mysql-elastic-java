package com.es.ElasticOperater;

import com.es.client.ElasticClient;
import org.dom4j.DocumentException;

import java.util.List;

/**
 *  es操作器，把数据中心的数据读取出来存放到Es中
 */
public interface ESOperater {
    //获取配置
    public void getEsConfig() throws DocumentException;
   //获取ES客户端
    public ElasticClient getClient();
    //初始化
    public void initialize();
}
