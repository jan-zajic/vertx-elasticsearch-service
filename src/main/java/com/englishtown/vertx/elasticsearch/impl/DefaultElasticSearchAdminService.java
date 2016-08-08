package com.englishtown.vertx.elasticsearch.impl;

import com.englishtown.vertx.elasticsearch.ElasticSearchAdminService;
import com.englishtown.vertx.elasticsearch.MappingOptions;
import com.englishtown.vertx.elasticsearch.internal.InternalElasticSearchAdminService;
import com.englishtown.vertx.elasticsearch.internal.InternalElasticSearchService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.AdminClient;

import javax.inject.Inject;
import java.util.List;

/**
 * Default implementation of {@link ElasticSearchAdminService}
 */
public class DefaultElasticSearchAdminService implements InternalElasticSearchAdminService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultElasticSearchAdminService.class);
    private final InternalElasticSearchService service;

    @Inject
    public DefaultElasticSearchAdminService(InternalElasticSearchService service) {
        this.service = service;
    }

    @Override
    public void putMapping(List<String> indices, String type, JsonObject source, MappingOptions options, Handler<AsyncResult<JsonObject>> resultHandler) {

        PutMappingRequestBuilder builder = getAdmin().indices()
                .preparePutMapping(indices.toArray(new String[indices.size()]))
                .setType(type)
                .setSource(source.encode());

        // TODO: PutMappingRequestBuilder setIgnoreConflicts() was removed in ES 2.0.0
//        if (options != null) {
//            if (options.shouldIgnoreConflicts() != null) builder.setIgnoreConflicts(options.shouldIgnoreConflicts());
//        }

        builder.execute(new ActionListener<PutMappingResponse>() {
            @Override
            public void onResponse(PutMappingResponse putMappingResponse) {
                JsonObject json = new JsonObject()
                        .put("acknowledged", putMappingResponse.isAcknowledged());
                resultHandler.handle(Future.succeededFuture(json));
            }

            @Override
            public void onFailure(Throwable e) {
            	onError("cannot put mapping for type "+type, e, resultHandler);
            }
        });

    }

    @Override
    public void createIndex(String index, JsonObject settings, JsonObject mappings, Handler<AsyncResult<JsonObject>> resultHandler) {
    	CreateIndexRequestBuilder builder = getAdmin().indices()
    			.prepareCreate(index);
    	if(settings != null)
    		builder.setSettings(settings.encode());
    	if(mappings != null) {
    		for (String key : mappings.getMap().keySet()) 
    		{
    			JsonObject source = mappings.getJsonObject(key);
    			builder.addMapping(key, source.encode());
    		}
    	}
    	
    	builder.execute(new ActionListener<CreateIndexResponse>() {
            @Override
            public void onResponse(CreateIndexResponse putMappingResponse) {
                JsonObject json = new JsonObject()
                        .put("acknowledged", putMappingResponse.isAcknowledged());
                resultHandler.handle(Future.succeededFuture(json));
            }

            @Override
            public void onFailure(Throwable e) {
            	onError("cannot create index "+index, e, resultHandler);
            }
        });
    }
    
    @Override
    public void deleteIndex(String index, Handler<AsyncResult<JsonObject>> resultHandler) {
    	DeleteIndexRequestBuilder builder = getAdmin().indices()
    			.prepareDelete(index);
    	
    	builder.execute(new ActionListener<DeleteIndexResponse>() {
            @Override
            public void onResponse(DeleteIndexResponse putMappingResponse) {
                JsonObject json = new JsonObject()
                        .put("acknowledged", putMappingResponse.isAcknowledged());
                resultHandler.handle(Future.succeededFuture(json));
            }

            @Override
            public void onFailure(Throwable e) {
            	onError("cannot delete index "+index, e, resultHandler);
            }
        });
    }
    
    /**
     * Returns the inner admin client
     *
     * @return
     */
    @Override
    public AdminClient getAdmin() {
        return service.getClient().admin();
    }

	private void onError(String message, Throwable t, Handler<AsyncResult<JsonObject>> resultHandler) 
	{
		service.onError(message, t, resultHandler);		
	}

    
}
