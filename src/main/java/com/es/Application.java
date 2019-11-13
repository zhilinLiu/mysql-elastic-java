package com.es;

import com.es.DataTransferCenter.DefaultDataCenter;
import com.es.client.ElasticClient;
import com.es.client.TransferApplication;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 */
public class Application {
    public static void main(String[] args) throws DocumentException, IOException {
        TransferApplication.start();
    }


}
