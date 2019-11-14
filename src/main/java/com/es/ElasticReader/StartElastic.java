package com.es.ElasticReader;

import com.es.client.ElasticClient;
import com.es.Application;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 *   该类从xml文件中获取配置文件，并且初始化ES客户端
 */

public class StartElastic implements ESOperater {
    private Map indexMap;
    private String host;
    private int port;
    private ElasticClient client;

    public StartElastic() {
        initialize();
    }

    @Override
    public void initialize() {
        try {
            getEsConfig();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("初始化配置 --------------------->  失败");
        }
        ElasticClient client = getClient();
        System.out.println("ElasticClient ----------------------->  初始化成功");
    }

    /**
     * get config from elastic-mysql.xml
     */
    @Override
    public void getEsConfig() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        File file = new File(Application.url.get(0));
        Document read = saxReader.read(file);
        Element root = read.getRootElement();
        Element esOperater = root.element("ESOperater");
        String host = esOperater.attribute("host").getValue();
        String portString = esOperater.attribute("port").getValue();
        int port = Integer.parseInt(portString);
        List<Element> indexes = esOperater.elements();
        LinkedHashMap<String, String> indexMap = new LinkedHashMap<>();
        indexes.forEach(index -> {
            String tablename = index.attribute("tablename").getValue();
            String shards = index.attributeValue("shards");
            String replicas = index.attributeValue("replicas");
            String indexJSON = index.getText();
            indexMap.put(tablename+":"+shards+":"+replicas, indexJSON.replaceAll(" ","").replaceAll("   ",""));
        });
        this.indexMap = indexMap;
        this.host = host;
        this.port = port;
    }


    @Override
    public ElasticClient getClient() {
        if(client == null){
            ElasticClient elasticClient = new ElasticClient().setHost(host, port);
            this.client=elasticClient;
            return elasticClient;
        }
        return this.client;
    }

    public Map getIndexMap() {
        return indexMap;
    }
}
