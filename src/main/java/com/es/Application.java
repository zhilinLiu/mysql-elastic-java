package com.es;

import com.es.DataTransferCenter.DefaultDataCenter;
import com.es.client.ElasticClient;
import com.es.client.TransferApplication;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class Application {
    public final static List<String> url= new ArrayList();
    public static void main(String[] args) throws DocumentException, IOException {
        url.add(args[0]);
       TransferApplication.start();
    }


}
