package com.es.client;

import com.es.Exception.CreateIndexException;
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
            try{
                boolean index = super.createIndex(tableName, client, shards, replicas, jsonMapping);
                return index;
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("创建索引失败，索引"+tableName+"已存在");
            }finally {
                client.close();
            }
            return true;
        }


        public boolean deleteIndex(String tableName) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            try{
                boolean b = super.deleteIndex(client, tableName);
                return b;
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }finally {
                client.close();
            }
        }


        public DocWriteResponse.Result putDoc(String tableName, Map jsonMap) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            try{
                DocWriteResponse.Result result = super.putDoc(client, tableName, jsonMap);
                return result;
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }finally {
                client.close();
            }
        }


        public DocWriteResponse.Result putDoc(String tableName, String id, Map jsonMap) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            try{
                DocWriteResponse.Result result = super.putDoc(client, tableName, id, jsonMap);
                return result;
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }finally {
                client.close();
            }
        }


        public Map getDoc(String tableName, String id) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            try{
                Map doc = super.getDoc(client, tableName, id);
                return doc;
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }finally {
                client.close();
            }
        }


        public RestStatus updateField(String tableName, String id, Map map) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            try{
                RestStatus restStatus = super.updateField(client, tableName, id, map);
                return restStatus;
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }finally {
                client.close();
            }
        }


        public DocWriteResponse.Result deleteDoc(String tableName, String id) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            try{
                DocWriteResponse.Result result = super.deleteDoc(tableName, id, client);
                return result;
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }finally {
                client.close();
            }
        }


        public List<Map> selectAll(String tableName) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            try{
                List<Map> list = super.selectAll(client, tableName);
                return list;
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }finally {
                client.close();
            }
        }


        public List<Map> selectAll(String tableName, int offset, int pageSize) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            try{
                List<Map> list = super.selectAll(client, tableName, offset, pageSize);
                return list;
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }finally {
                client.close();
            }
        }


        public ArrayList<Map> exactSelect(String tableName, String field, String value) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            try{
                ArrayList<Map> list = super.exactSelect(client, tableName, field, value);
                return list;
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }finally {
                client.close();
            }
        }


        public ArrayList<Map> selectByIds(String tableName, List<? extends String> ids) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            try{
                ArrayList<Map> list = super.selectByIds(client, tableName, ids);
                return list;
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }finally {
                client.close();
            }
        }


        public ArrayList<Map> search(String tableName, String field, Object value) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            try{
                ArrayList<Map> index = super.search(client, tableName, field, value);
                return index;
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }finally {
                client.close();
            }
        }


        public ArrayList<Map> search(String tableName, String[] fields, Object value, String importantField) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            try{
                ArrayList<Map> index = super.search(client, tableName, fields, value, importantField);
                return index;
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }finally {
                client.close();
            }
        }


        public ArrayList<Map> search(String tableName, String[] fields, Object value) throws IOException {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(host));
            try{
                ArrayList<Map> index = super.search(client, tableName, fields, value);
                return index;
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }finally {
                client.close();
            }
        }
    }
}
