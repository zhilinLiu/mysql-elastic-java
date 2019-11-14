package com.es.reader.MysqlReader;

import com.es.Application;
import com.es.Exception.DataReaderException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


import java.io.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * 读取数据实现类
 */
public class Reader implements MysqlReader {
    private ConfigReader config;
    private Connection conn;
    private Map<String, List> data;

    public Reader() {
        initializeConfigReader();
        initializeMysqlReader();
    }

    //初始化XML读取器
    private void initializeConfigReader() {
        config = new ConfigReader();
        Document document = config.configRead(Application.url.get(0));
        ConfigReader config = this.config.getConfig(document);
        this.config = config;
    }

    //初始化数据库连接池
    private void initializeMysqlReader() {
        DataReader dataReader = new DataReader(config);
        System.out.println("启动数据读取器:    true");
        this.conn = dataReader.getJDBCConnection();
        boolean b = ReadDataFromMysql();
        System.out.println("读取mysql数据:  " + b);
    }

    /**
     *  业务定制版
     */
    //这块容易报错
    @Override
    public boolean ReadDataFromMysql() {
        Map sqlMap = config.getSqlMap();
        LinkedHashMap<String, List> map = new LinkedHashMap<>();
        sqlMap.forEach((tableName, value1) -> {
            LinkedList<List> rows = new LinkedList<>();
            String value = (String) value1;
            String sql = "";
            //如果开启了增量数据，则sql添加筛选条件
            if(tableName.equals("pr_jigou")){
                if (Boolean.parseBoolean(config.getConfigMap().get("use").toString())&&config.getConfigMap().get(tableName)!=null) {
                    sql = "select  " + value + " from " + tableName + " where id>" + config.getConfigMap().get(tableName).toString()+" and state=2";
                } else {
                    sql = "select  " + value + " from " + tableName+" where state=2";
                }
            }else {
                if (Boolean.parseBoolean(config.getConfigMap().get("use").toString()) && config.getConfigMap().get(tableName) != null) {
                    sql = "select  " + value + " from " + tableName + " where id>" + config.getConfigMap().get(tableName).toString();
                } else {
                    sql = "select  " + value + " from " + tableName;
                }
            }


            String[] feilds = value.split(",");
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                //如果resultSet还有数据
                while (resultSet.next()) {
                    LinkedList<Object> row = new LinkedList<>();
                    for (String feild : feilds) {
                        Object object = resultSet.getObject(feild);
                        row.add(object);
                    }
                    rows.add(row);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DataReaderException("从表中读取数据失败");
            }
            //如果开启了增量，获取当前最大id，并且记录到xml文件中
            Integer max=0;
            if(rows.size()>0){
                max = rows.stream().map(x -> (int) x.get(0)).max((a, b) -> {
                    if (a > b) {
                        return 1;
                    } else return -1;
                }).get();
                //放入xml文件中
                putMaxNum((String) tableName,max);
            }
            map.put(tableName.toString() + ":" + value, rows);
        });
        this.data = map;
        return true;
    }

    @Override
    public Map getData() {
        return this.data;
    }

    public void putMaxNum(String tableName,int max) {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
//            URL url = getClass().getClassLoader().getResource("elastic-mysql.xml");
            File file = new File(Application.url.get(0));
            document = saxReader.read(file);
            Element rootElement = document.getRootElement();
            Element increment = rootElement.element("increment");
            Element table=null;
            if(increment.element(tableName)==null){
                table = increment.addElement(tableName);
            }else {
                table = increment.element(tableName);
            }
            table.addAttribute("current-id",String.valueOf(max));
            XMLWriter xmlWriter = new XMLWriter(new OutputStreamWriter(new FileOutputStream(Application.url.get(0)),"UTF-8"));
            xmlWriter.write(document);
            xmlWriter.close();
        } catch (Exception e) {
            System.out.println("写入文件"+tableName+"的增量数据到"+Application.url.get(0)+"失败，没有写入权限，请检查");
        }

    }

}
