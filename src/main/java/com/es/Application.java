package com.es;

import com.es.DataTransferCenter.DefaultDataCenter;
import com.es.client.ElasticClient;
import com.es.client.TransferApplication;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 */
public class Application {
    public final static List<String> url= new ArrayList();
    public static void main(String[] args) throws DocumentException, IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println("-------------------------------------"+simpleDateFormat.format(new Date())+"------------------------------------------------------------");
        url.add(args[0]);
       TransferApplication.start();
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
    }


}
