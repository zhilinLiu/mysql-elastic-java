package com.es;

import com.es.DataTransferCenter.DefaultDataCenter;
import com.es.client.TransferApplication;
import org.dom4j.DocumentException;

import java.io.IOException;

/**
 *
 */
public class Application {
    public static void main(String[] args) throws DocumentException, IOException {
        TransferApplication.start();
    }
}
