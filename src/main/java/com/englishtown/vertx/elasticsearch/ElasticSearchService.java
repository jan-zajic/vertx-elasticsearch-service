package com.englishtown.vertx.elasticsearch;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.ProxyIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;

import java.util.Collections;
import java.util.List;

/**
 * ElasticSearch service
 */
@VertxGen
@ProxyGen
public interface ElasticSearchService {

    static ElasticSearchService createEventBusProxy(Vertx vertx, String address) {
        return ProxyHelper.createProxy(ElasticSearchService.class, vertx, address);
    }

    @ProxyIgnore
    void start();

    @ProxyIgnore
    void stop();

    /**
     * http://www.elastic.co/guide/en/elasticsearch/client/java-api/1.4/index_.html
     *
     * @param options       optional index options (id, timeout, ttl, etc.)
     * @param resultHandler result handler callback
     */
    void index(IndexOptions options, Handler<AsyncResult<JsonObject>> resultHandler);

    /**
     * http://www.elastic.co/guide/en/elasticsearch/client/java-api/1.4/java-update-api.html
     *
     * @param options       the update options (doc, script, etc.)
     * @param resultHandler result handler callback
     */
    void update(UpdateOptions options, Handler<AsyncResult<JsonObject>> resultHandler);

    /**
     * https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-docs-bulk.html
     *
     * @param options       the bulk options
     * @param resultHandler result handler callback
     */
    void bulk(BulkOptions options, Handler<AsyncResult<JsonObject>> resultHandler);
     
    /**
     * http://www.elastic.co/guide/en/elasticsearch/client/java-api/1.4/get.html
     *
     * @param index         the index name
     * @param type          the type name
     * @param id            the source id to update
     * @param resultHandler result handler callback
     */
    @GenIgnore
    @ProxyIgnore
    default void get(String index, String type, String id, Handler<AsyncResult<JsonObject>> resultHandler) {
        get(index, type, id, new GetOptions(), resultHandler);
    }

    /**
     * http://www.elastic.co/guide/en/elasticsearch/client/java-api/1.4/get.html
     *
     * @param index         the index name
     * @param type          the type name
     * @param id            the source id to update
     * @param options       the update options
     * @param resultHandler result handler callback
     */
    void get(String index, String type, String id, GetOptions options, Handler<AsyncResult<JsonObject>> resultHandler);

    @GenIgnore
    @ProxyIgnore
    default void search(String index, Handler<AsyncResult<JsonObject>> resultHandler) {
        search(index, new SearchOptions(), resultHandler);
    }

    @GenIgnore
    @ProxyIgnore
    default void search(String index, SearchOptions options, Handler<AsyncResult<JsonObject>> resultHandler) {
        search(Collections.singletonList(index), options, resultHandler);
    }

    @GenIgnore
    @ProxyIgnore
    default void search(List<String> indices, Handler<AsyncResult<JsonObject>> resultHandler) {
        search(indices, new SearchOptions(), resultHandler);
    }

    void search(List<String> indices, SearchOptions options, Handler<AsyncResult<JsonObject>> resultHandler);

    @GenIgnore
    @ProxyIgnore
    default void searchScroll(String scrollId, Handler<AsyncResult<JsonObject>> resultHandler) {
        searchScroll(scrollId, new SearchScrollOptions(), resultHandler);
    }

    /**
     * http://www.elastic.co/guide/en/elasticsearch/reference/1.4/search-request-scroll.html
     *
     * @param scrollId
     * @param options
     * @param resultHandler
     */
    void searchScroll(String scrollId, SearchScrollOptions options, Handler<AsyncResult<JsonObject>> resultHandler);

    /**
     * http://www.elastic.co/guide/en/elasticsearch/client/java-api/1.4/delete.html
     *
     * @param options       optional delete options (timeout, etc.)
     * @param resultHandler result handler callback
     */
    void delete(DeleteOptions options, Handler<AsyncResult<JsonObject>> resultHandler);

    /**
     * https://www.elastic.co/guide/en/elasticsearch/reference/current/search-suggesters.html
     *
     * @param index         the index name
     * @param options       optional suggest options
     * @param resultHandler result handler callback
     */
    void suggest(String index, SuggestOptions options, Handler<AsyncResult<JsonObject>> resultHandler);

}
