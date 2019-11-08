package com.es.client;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 17点26分
 * lzl
 */

public class ElasticClient {

    private HttpHost host;

    private SimpleES ops;

    public ElasticClient() {
        this.ops = new SimpleES();
    }

    public ElasticClient setHost(String host, int port) {
        HttpHost httpHost = new HttpHost(host, port);
        this.host = httpHost;
        return this;
    }

    public SimpleES ops() throws IOException {
        if (ops == null) {
            SimpleES simpleES = new SimpleES();
            this.ops = simpleES;
            return simpleES;
        }
        return this.ops;
    }

    public class SimpleES extends OperateES {

        public boolean createIndex(String tableName, int shards, int replicas, String jsonMapping) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            boolean index = super.createIndex(tableName, client, shards, replicas, jsonMapping);
            client.close();
            return index;
        }


        public boolean deleteIndex(String tableName) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            boolean b = super.deleteIndex(client, tableName);
            client.close();
            return b;
        }


        public DocWriteResponse.Result putDoc(String tableName, Map jsonMap) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            DocWriteResponse.Result result = super.putDoc(client, tableName, jsonMap);
            client.close();
            return result;
        }


        public DocWriteResponse.Result putDoc(String tableName, String id, Map jsonMap) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            DocWriteResponse.Result result = super.putDoc(client, tableName, id, jsonMap);
            client.close();
            return result;
        }


        public Map getDoc(String tableName, String id) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            Map doc = super.getDoc(client, tableName, id);
            client.close();
            return doc;
        }


        public RestStatus updateField(String tableName, String id, Map map) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            RestStatus restStatus = super.updateField(client, tableName, id, map);
            client.close();
            return restStatus;
        }


        public DocWriteResponse.Result deleteDoc(String tableName, String id) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            DocWriteResponse.Result result = super.deleteDoc(tableName, id, client);
            client.close();
            return result;
        }


        public List<Map> selectAll(String tableName) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            List<Map> list = super.selectAll(client, tableName);
            client.close();
            return list;
        }


        public List<Map> selectAll(String tableName, int offset, int pageSize) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            List<Map> list = super.selectAll(client, tableName, offset, pageSize);
            client.close();
            return list;
        }


        public ArrayList<Map> exactSelect(String tableName, String field, String value) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            ArrayList<Map> list = super.exactSelect(client, tableName, field, value);
            client.close();
            return list;
        }


        public ArrayList<Map> selectByIds(String tableName, List<? extends String> ids) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            ArrayList<Map> list = super.selectByIds(client, tableName, ids);
            client.close();
            return list;
        }


        public ArrayList<Map> search(String tableName, String field, Object value) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            ArrayList<Map> index = super.search(client, tableName, field, value);
            client.close();
            return index;
        }


        public ArrayList<Map> search(String tableName, String[] fields, Object value, String importantField) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            ArrayList<Map> index = super.search(client, tableName, fields, value, importantField);
            client.close();
            return index;
        }


        public ArrayList<Map> search(String tableName, String[] fields, Object value) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            ArrayList<Map> index = super.search(client, tableName, fields, value);
            client.close();
            return index;
        }
    }
}
