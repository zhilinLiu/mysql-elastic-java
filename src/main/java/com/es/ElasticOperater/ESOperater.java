package com.es.ElasticOperater;

import java.util.List;

/**
 *  es操作器，把数据中心的数据读取出来存放到Es中
 */
public interface ESOperater {
    //从myRead获取数据
    public boolean getData();

    //得到数据后做一些转换操作
    public void dataTransfer();

    //提供给es操作器的接口，输送转换后的数据
    public List getTranferData();
}
