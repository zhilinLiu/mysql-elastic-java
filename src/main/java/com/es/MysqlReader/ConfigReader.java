package com.es.MysqlReader;

import com.es.Exception.ConfigReaderException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ConfigReader {
    private Map sqlMap = new HashMap<String, String>();
    private Map configMap = new HashMap<String, String>();

    //获取document节点
    public Document configRead(String url)  {
        Document read = null;
        try {
            SAXReader saxReader = new SAXReader();
            read = saxReader.read(getClass().getClassLoader().getResource(url));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConfigReaderException("read config failed , may be the path error");
        }

        return read;
    }

    //读取配置放入map中
    public ConfigReader getConfig(Document document) {
        //根节点
        Element root = document.getRootElement();
        //mysqlReader节点
        Element mysqlReader = root.element("mysqlReader");
        //遍历mysqlReader下所有节点
        List<Element> elements = mysqlReader.elements();
        elements.forEach(element -> {
            if (element.getName().equals("mysql-url")) {
                String url = element.attribute("url").getValue();
                configMap.put("url", url);
            }
            if (element.getName().equals("tables")) {
                List<Element> tables = element.elements();
                tables.forEach(table -> {
                    String tableName = table.attribute("tablename").getValue();
                    StringBuffer stringBuffer = new StringBuffer();
                    List<Element> fields = table.elements();
                    fields.forEach(field -> {
                        stringBuffer.append(field.getText() + ",");
                    });
                    String select = stringBuffer.substring(0, stringBuffer.length() - 1);
                    String sql = "select " + select + " from " + tableName;
                    sqlMap.put(tableName, sql);
                });
            }
        });
        return this;
    }

    public Map getSqlMap() {
        return sqlMap;
    }

    public Map getConfigMap() {
        return configMap;
    }
}
