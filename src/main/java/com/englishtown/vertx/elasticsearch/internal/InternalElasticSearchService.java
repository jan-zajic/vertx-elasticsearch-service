package com.englishtown.vertx.elasticsearch.internal;

import org.elasticsearch.client.transport.TransportClient;

import com.englishtown.vertx.elasticsearch.ElasticSearchService;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * Internal
 */
public interface InternalElasticSearchService extends ElasticSearchService {

    /**
     * Return the inner {@link TransportClient}
     */
    TransportClient getClient();
    
    void onError(String message, Throwable t, Handler<AsyncResult<JsonObject>> resultHandler);

}
