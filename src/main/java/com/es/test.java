package com.es;

import com.es.DataTransferCenter.DataCenterAbstract;
import com.es.ElasticOperater.StartElastic;
import com.es.client.ElasticClient;
import com.es.reader.MysqlReader.Reader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.util.Map;

/**
 *
 */
public class test {
    public static void main(String[] args) throws DocumentException, IOException {
        DataCenterAbstract dataCenterAbstract = new DataCenterAbstract() {
            @Override
            public boolean intoES(ElasticClient client, Map mysqlData, Map indexMap) {
                
                return true;
            }
        };



    }
}
