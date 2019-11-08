package com.es.client;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 16点38分 刘治麟
 * es操作类
 */
public class OperateES {
    //创建索引库
    public boolean createIndex(String tableName, RestHighLevelClient client, String jsonMapping) throws IOException {

        CreateIndexRequest createIndexRequest = new CreateIndexRequest(tableName);
        createIndexRequest.settings(Settings.builder().put("number_of_shards", 1).put("number_of_replicas", 0));
        createIndexRequest.mapping("doc", jsonMapping, XContentType.JSON);
        //创建索引操作客户端
        IndicesClient indices = client.indices();
        //创建响应对象
        CreateIndexResponse createIndexResponse = indices.create(createIndexRequest);
        //得到响应结果
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
        return shardsAcknowledged;

    }

    public boolean createIndex(String tableName, RestHighLevelClient client, int shards, int replicas, String jsonMapping) throws IOException {

        CreateIndexRequest createIndexRequest = new CreateIndexRequest(tableName);
        createIndexRequest.settings(Settings.builder().put("number_of_shards", shards).put("number_of_replicas", replicas));
        createIndexRequest.mapping("doc", jsonMapping, XContentType.JSON);
        //创建索引操作客户端
        IndicesClient indices = client.indices();
        //创建响应对象
        CreateIndexResponse createIndexResponse = indices.create(createIndexRequest);
        //得到响应结果
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();

        return shardsAcknowledged;

    }

    //删除index
    public boolean deleteIndex(RestHighLevelClient client, String tableName) throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(tableName);
        DeleteIndexResponse response = client.indices().delete(deleteIndexRequest);

        return response.isAcknowledged();
    }

    //添加doc,自动生成ID
    public DocWriteResponse.Result putDoc(RestHighLevelClient client, String tableName, Map jsonMap) throws IOException {
        //索引请求对象
        IndexRequest indexRequest = new IndexRequest(tableName, "doc");
        //指定索引文档内容
        indexRequest.source(jsonMap);
        //索引响应对象
        IndexResponse indexResponse = client.index(indexRequest);
        //获取响应结果
        DocWriteResponse.Result result = indexResponse.getResult();

        return result;
    }

    //传入ID
    public DocWriteResponse.Result putDoc(RestHighLevelClient client, String tableName, String id, Map jsonMap) throws IOException {
        //索引请求对象
        IndexRequest indexRequest = new IndexRequest(tableName, "doc", id);
        //指定索引文档内容
        indexRequest.source(jsonMap);
        //索引响应对象
        IndexResponse indexResponse = client.index(indexRequest);
        //获取响应结果
        DocWriteResponse.Result result = indexResponse.getResult();

        return result;
    }

    //查询doc,有id
    public Map getDoc(RestHighLevelClient client, String tableName, String id) throws IOException {
        GetRequest getRequest = new GetRequest(tableName, "doc", id);
        GetResponse response = client.get(getRequest);
        if (response.isExists()) {
            Map<String, Object> sourceAsMap = response.getSourceAsMap();
            return sourceAsMap;
        }
        return null;
    }

    //根据传入的Id去更新这行数据的字段
    public RestStatus updateField(RestHighLevelClient client, String tableName, String id, Map map) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(tableName, "doc", id);
        updateRequest.doc(map);
        UpdateResponse update = client.update(updateRequest);
        RestStatus status = update.status();
        return status;
    }

    //delete row according to ID
    public DocWriteResponse.Result deleteDoc(String tableName, String id,RestHighLevelClient client) throws IOException {
        DeleteRequest doc = new DeleteRequest(tableName, "doc", id);
        //the result of response
        DeleteResponse delete = client.delete(doc);
        DocWriteResponse.Result result = delete.getResult();
        return result;
    }

    //select all date
    public List<Map> selectAll(RestHighLevelClient client, String tableName) throws IOException {
        //创建搜索请求 =》搜索表名
        SearchRequest request = new SearchRequest(tableName);
        request.types("doc");
        //创建请求携带的搜索源 =》搜索哪些字段，不设置则搜索全部
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
        //选取字段，包含哪些字段，不包含哪些字段
        builder.fetchSource(new String[]{"name", "studymodel", "description"}, new String[]{});
        //默认未设置
        request.source();
        SearchResponse response = client.search(request);
        SearchHit[] hits = response.getHits().getHits();
        ArrayList<Map> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            list.add(sourceAsMap);
        }
//        
        return list;
    }

    //分页查询
    public List<Map> selectAll(RestHighLevelClient client, String tableName, int offset, int pageSize) throws IOException {
        SearchRequest request = new SearchRequest(tableName);
        request.types("doc");
        //创建请求携带的搜索源 =》搜索哪些字段，不设置则搜索全部
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
        //选取字段，包含哪些字段，不包含哪些字段
//        builder.fetchSource(new String[]{"name","studymodel","description"},new String[]{});
        builder.from(offset);//当前页数
        builder.size(pageSize);//分页显示个数
        //默认未设置
        request.source(builder);
        SearchResponse response = client.search(request);
        SearchHit[] hits = response.getHits().getHits();
        ArrayList<Map> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            list.add(sourceAsMap);
        }

        return list;
    }

    //精确查询
    public ArrayList<Map> exactSelect(RestHighLevelClient client, String tableName, String field, String value) throws IOException {
        SearchRequest request = new SearchRequest(tableName);
        request.types("doc");
        //创建请求携带的搜索源 =》搜索哪些字段，不设置则搜索全部
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery(field, value));
        //选取字段，包含哪些字段，不包含哪些字段
//        builder.fetchSource(new String[]{"name","studymodel","description"},new String[]{});
        //默认未设置
        request.source(builder);
        SearchResponse response = client.search(request);
        SearchHit[] hits = response.getHits().getHits();
        ArrayList<Map> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            list.add(sourceAsMap);
        }

        return list;
    }

    //根据ID列表查询
    public ArrayList<Map> selectByIds(RestHighLevelClient client, String tableName, List<? extends String> ids) throws IOException {
        SearchRequest request = new SearchRequest(tableName);
        request.types("doc");
        //创建请求携带的搜索源 =》搜索哪些字段，不设置则搜索全部
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termsQuery("_id", ids));
        //选取字段，包含哪些字段，不包含哪些字段
//        builder.fetchSource(new String[]{"name","studymodel","description"},new String[]{});
        //默认未设置
        request.source(builder);
        SearchResponse response = client.search(request);
        SearchHit[] hits = response.getHits().getHits();
        ArrayList<Map> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            list.add(sourceAsMap);
        }

        return list;
    }

    //全文检索
    public ArrayList<Map> search(RestHighLevelClient client, String tableName, String field, Object value) throws IOException {
        SearchRequest request = new SearchRequest(tableName);
        request.types("doc");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery(field, value).operator(Operator.OR)); //匹配机制
        request.source(builder);
        SearchResponse response = client.search(request);
        SearchHit[] hits = response.getHits().getHits();
        ArrayList<Map> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            list.add(sourceAsMap);
        }

        return list;
    }

    //全文检索 --- 多字段匹配-有最重要关键字
    public ArrayList<Map> search(RestHighLevelClient client, String tableName, String[] fields, Object value, String importantField) throws IOException {
        SearchRequest request = new SearchRequest(tableName);
        request.types("doc");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(value, fields).minimumShouldMatch("50%");
        multiMatchQueryBuilder.field(importantField, 10);//最重要的关键字提升10倍权重
        builder.query(multiMatchQueryBuilder); //匹配机制
        request.source(builder);
        SearchResponse response = client.search(request);
        SearchHit[] hits = response.getHits().getHits();
        ArrayList<Map> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            list.add(sourceAsMap);
        }

        return list;
    }

    //全文检索 --- 多字段匹配--无重要关键字
    public ArrayList<Map> search(RestHighLevelClient client, String tableName, String[] fields, Object value) throws IOException {
        SearchRequest request = new SearchRequest(tableName);
        request.types("doc");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(value, fields).minimumShouldMatch("50%");
        builder.query(multiMatchQueryBuilder); //匹配机制
        request.source(builder);
        SearchResponse response = client.search(request);
        SearchHit[] hits = response.getHits().getHits();
        ArrayList<Map> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            list.add(sourceAsMap);
        }
        return list;
    }
}
