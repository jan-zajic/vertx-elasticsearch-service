package com.englishtown.vertx.elasticsearch.internal;

import org.elasticsearch.client.AdminClient;

import com.englishtown.vertx.elasticsearch.ElasticSearchAdminService;

import io.vertx.codegen.annotations.ProxyIgnore;

/**
 * Internal admin service
 */
public interface InternalElasticSearchAdminService extends ElasticSearchAdminService {

    /**
     * Returns the inner admin client
     *
     * @return
     */
    @ProxyIgnore
    AdminClient getAdmin();
}
