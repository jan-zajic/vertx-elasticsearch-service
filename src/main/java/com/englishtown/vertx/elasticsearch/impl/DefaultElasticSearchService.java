package com.englishtown.vertx.elasticsearch.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkItemResponse.Failure;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequestBuilder;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequestBuilder;
import org.elasticsearch.action.suggest.SuggestRequestBuilder;
import org.elasticsearch.action.suggest.SuggestResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.reindex.BulkIndexByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.Template;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.elasticsearch.transport.RemoteTransportException;

import com.englishtown.vertx.elasticsearch.AbstractWriteOptions;
import com.englishtown.vertx.elasticsearch.BulkOptions;
import com.englishtown.vertx.elasticsearch.DeleteOptions;
import com.englishtown.vertx.elasticsearch.ElasticSearchConfigurator;
import com.englishtown.vertx.elasticsearch.GetKey;
import com.englishtown.vertx.elasticsearch.GetOptions;
import com.englishtown.vertx.elasticsearch.IndexOptions;
import com.englishtown.vertx.elasticsearch.MultiGetRequest;
import com.englishtown.vertx.elasticsearch.SearchOptions;
import com.englishtown.vertx.elasticsearch.SearchScrollOptions;
import com.englishtown.vertx.elasticsearch.SuggestOptions;
import com.englishtown.vertx.elasticsearch.TransportClientFactory;
import com.englishtown.vertx.elasticsearch.UpdateByQueryOptions;
import com.englishtown.vertx.elasticsearch.UpdateOptions;
import com.englishtown.vertx.elasticsearch.internal.InternalElasticSearchService;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Default implementation of
 * {@link com.englishtown.vertx.elasticsearch.ElasticSearchService}
 */
public class DefaultElasticSearchService implements InternalElasticSearchService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultElasticSearchService.class);

	private final TransportClientFactory clientFactory;
	private final ElasticSearchConfigurator configurator;
	protected TransportClient client;

	public static final String CONST_ID = "id";
	public static final String CONST_INDEX = "index";
	public static final String CONST_TYPE = "type";
	public static final String CONST_VERSION = "version";
	public static final String CONST_SOURCE = "source";
	public static final String CONST_CREATED = "created";

	public static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

	@Inject
	public DefaultElasticSearchService(TransportClientFactory clientFactory, ElasticSearchConfigurator configurator) {
		this.clientFactory = clientFactory;
		this.configurator = configurator;
	}

	@Override
	public void start() {
		Settings.setSettingsRequireUnits(configurator.getSettingsRequireUnits());

		Settings settings = Settings
				.builder()
					.put("cluster.name", configurator.getClusterName())
					.put("client.transport.sniff", configurator.getClientTransportSniff())
					.build();

		client = clientFactory.create(settings);
		configurator.getTransportAddresses().forEach(client::addTransportAddress);

	}

	@Override
	public void stop() {
		client.close();
		client = null;
	}

	@Override
	public void index(IndexOptions options, Handler<AsyncResult<JsonObject>> resultHandler) {
		IndexRequestBuilder builder = convert(options);

		builder.execute(new ActionListener<IndexResponse>() {
			@Override
			public void onResponse(IndexResponse indexResponse) {
				JsonObject result = new JsonObject()
						.put(CONST_INDEX, indexResponse.getIndex())
							.put(CONST_TYPE, indexResponse.getType())
							.put(CONST_ID, indexResponse.getId())
							.put(CONST_VERSION, indexResponse.getVersion())
							.put(CONST_CREATED, indexResponse.isCreated());
				resultHandler.handle(Future.succeededFuture(result));
			}

			@Override
			public void onFailure(Throwable t) {
				onError("cannot index " + options.getId(), t, resultHandler);
			}

		});

	}

	private IndexRequestBuilder convert(IndexOptions options) {
		IndexRequestBuilder builder = client
				.prepareIndex(options.getIndex(), options.getType())
					.setSource(options.getDoc().encode());

		if (options != null) {
			if (options.getId() != null)
				builder.setId(options.getId());
			if (options.getRouting() != null)
				builder.setRouting(options.getRouting());
			if (options.getParent() != null)
				builder.setParent(options.getParent());
			if (options.getOpType() != null)
				builder.setOpType(options.getOpType());
			if (options.isRefresh() != null)
				builder.setRefresh(options.isRefresh());
			if (options.getConsistencyLevel() != null)
				builder.setConsistencyLevel(options.getConsistencyLevel());
			if (options.getVersion() != null)
				builder.setVersion(options.getVersion());
			if (options.getVersionType() != null)
				builder.setVersionType(options.getVersionType());
			if (options.getTimestamp() != null)
				builder.setTimestamp(options.getTimestamp());
			if (options.getTtl() != null)
				builder.setTTL(options.getTtl());
			if (options.getTimeout() != null)
				builder.setTimeout(options.getTimeout());
		}

		return builder;
	}

	@Override
	public void update(UpdateOptions options, Handler<AsyncResult<JsonObject>> resultHandler) {
		UpdateRequestBuilder builder = convert(options);

		builder.execute(new ActionListener<UpdateResponse>() {
			@Override
			public void onResponse(UpdateResponse updateResponse) {
				JsonObject result = new JsonObject()
						.put(CONST_INDEX, updateResponse.getIndex())
							.put(CONST_TYPE, updateResponse.getType())
							.put(CONST_ID, updateResponse.getId())
							.put(CONST_VERSION, updateResponse.getVersion())
							.put(CONST_CREATED, updateResponse.isCreated());
				resultHandler.handle(Future.succeededFuture(result));
			}

			@Override
			public void onFailure(Throwable e) {
				onError("cannot update " + options.getId(), e, resultHandler);
			}
		});

	}

	private UpdateRequestBuilder convert(UpdateOptions options) {
		UpdateRequestBuilder builder = client.prepareUpdate(options.getIndex(), options.getType(), options.getId());

		if (options != null) {
			if (options.getRouting() != null)
				builder.setRouting(options.getRouting());
			if (options.getParent() != null)
				builder.setParent(options.getParent());
			if (options.isRefresh() != null)
				builder.setRefresh(options.isRefresh());
			if (options.getConsistencyLevel() != null)
				builder.setConsistencyLevel(options.getConsistencyLevel());
			if (options.getVersion() != null)
				builder.setVersion(options.getVersion());
			if (options.getVersionType() != null)
				builder.setVersionType(options.getVersionType());
			if (options.getTimeout() != null)
				builder.setTimeout(options.getTimeout());

			if (options.getRetryOnConflict() != null)
				builder.setRetryOnConflict(options.getRetryOnConflict());
			if (options.getDoc() != null)
				builder.setDoc(options.getDoc().encode());
			if (options.getUpsert() != null)
				builder.setUpsert(options.getUpsert().encode());
			if (options.isDocAsUpsert() != null)
				builder.setDocAsUpsert(options.isDocAsUpsert());
			if (options.isDetectNoop() != null)
				builder.setDetectNoop(options.isDetectNoop());
			if (options.isScriptedUpsert() != null)
				builder.setScriptedUpsert(options.isScriptedUpsert());

			if (options.getScript() != null) {
				if (options.getScriptType() != null) {
					Map<String, ? extends Object> params = (options.getScriptParams() == null ? null : options.getScriptParams().getMap());
					builder.setScript(new Script(options.getScript(), options.getScriptType(), options.getScriptLang(), params));
				} else {
					builder.setScript(new Script(options.getScript()));
				}
			}
			if (!options.getFields().isEmpty()) {
				builder.setFields(options.getFields().toArray(new String[options.getFields().size()]));
			}
		}

		return builder;
	}

	@Override
	public void updateByQuery(List<String> indices, UpdateByQueryOptions options, Handler<AsyncResult<JsonObject>> resultHandler) {
		UpdateByQueryRequestBuilder builder = convert(indices, options);
		builder.execute(new ActionListener<BulkIndexByScrollResponse>() {
			@Override
			public void onResponse(BulkIndexByScrollResponse updateResponse) {
				JsonObject result = new JsonObject();
				JsonArray items = new JsonArray();
				for (Failure bulkItemResponse : updateResponse.getIndexingFailures()) {
					JsonObject itemResult = new JsonObject()
							.put(CONST_INDEX, bulkItemResponse.getIndex())
								.put(CONST_TYPE, bulkItemResponse.getType())
								.put(CONST_ID, bulkItemResponse.getId());
					itemResult.put("failureMessage", bulkItemResponse.getMessage());
					//TODO: getCause
					items.add(itemResult);
				}
				result.put("failures", items);
				result.put("tookInMillis", updateResponse.getTook().getMillis());
				result.put("updated", updateResponse.getUpdated());
				result.put("batches", updateResponse.getBatches());
				result.put("versionConflicts", updateResponse.getVersionConflicts());
				resultHandler.handle(Future.succeededFuture(result));
			}

			@Override
			public void onFailure(Throwable e) {
				onError("cannot update by query", e, resultHandler);
			}
		});
	}

	private UpdateByQueryRequestBuilder convert(List<String> indices, UpdateByQueryOptions options) {
		UpdateByQueryRequestBuilder builder = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
		if (indices != null)
			builder.source(indices.toArray(new String[] {}));
		if (options.getScript() != null) {
			if (options.getScriptType() != null) {
				Map<String, ? extends Object> params = (options.getScriptParams() == null ? null : options.getScriptParams().getMap());
				builder.script(new Script(options.getScript(), options.getScriptType(), options.getScriptLang(), params));
			} else {
				builder.script(new Script(options.getScript()));
			}
		}
		
		applySearchOptions(options, builder.source());
		if(options.getAbortOnVersionConflict() != null)
			builder.abortOnVersionConflict(options.getAbortOnVersionConflict());
		if (options.getRefresh() != null)
			builder.refresh(options.getRefresh());
		if (options.getConsistencyLevel() != null)
			builder.consistency(options.getConsistencyLevel());
		return builder;
	}

	@Override
	public void bulk(BulkOptions options, Handler<AsyncResult<JsonObject>> resultHandler) {
		BulkRequestBuilder builder = client.prepareBulk();
		if (options != null) {
			if (options.isRefresh() != null)
				builder.setRefresh(options.isRefresh());
			if (options.getConsistencyLevel() != null)
				builder.setConsistencyLevel(options.getConsistencyLevel());
			if (options.getTimeout() != null)
				builder.setTimeout(options.getTimeout());

			for (AbstractWriteOptions<?> writeOptions : options.getWriteOptions()) {
				switch (writeOptions.getClass().getSimpleName()) {
				case "DeleteOptions":
					builder.add(createDeleteRequest((DeleteOptions) writeOptions));
					break;
				case "IndexOptions":
					builder.add(createIndexRequest((IndexOptions) writeOptions));
					break;
				case "UpdateOptions":
					builder.add(createUpdateRequest((UpdateOptions) writeOptions));
					break;
				}
			}
		}

		builder.execute(new ActionListener<BulkResponse>() {
			@Override
			public void onResponse(BulkResponse bulkResponse) {
				JsonObject result = new JsonObject();
				JsonArray items = new JsonArray();
				for (BulkItemResponse bulkItemResponse : bulkResponse.getItems()) {
					JsonObject itemResult = new JsonObject()
							.put(CONST_INDEX, bulkItemResponse.getIndex())
								.put(CONST_TYPE, bulkItemResponse.getType())
								.put(CONST_ID, bulkItemResponse.getId())
								.put(CONST_VERSION, bulkItemResponse.getVersion());
					if (bulkItemResponse.getFailure() != null) {
						Failure failure = bulkItemResponse.getFailure();
						itemResult.put("failure", true);
						itemResult.put("failureMessage", failure.getMessage());
					}
					items.add(itemResult);
				}
				result.put("items", items);
				result.put("tookInMillis", bulkResponse.getTookInMillis());
				resultHandler.handle(Future.succeededFuture(result));
			}

			@Override
			public void onFailure(Throwable e) {
				onError("cannot execute bulk", e, resultHandler);
			}
		});
	}

	private UpdateRequest createUpdateRequest(UpdateOptions writeOptions) {
		UpdateRequestBuilder convert = convert(writeOptions);
		return convert.request();
	}

	private IndexRequest createIndexRequest(IndexOptions writeOptions) {
		IndexRequestBuilder convert = convert(writeOptions);
		return convert.request();
	}

	private DeleteRequest createDeleteRequest(DeleteOptions writeOptions) {
		DeleteRequestBuilder convert = convert(writeOptions);
		return convert.request();
	}

	@Override
	public void get(String index, String type, String id, GetOptions options, Handler<AsyncResult<JsonObject>> resultHandler) {

		GetRequestBuilder builder = client.prepareGet(index, type, id);

		if (options != null) {
			if (options.getRouting() != null)
				builder.setRouting(options.getRouting());
			if (options.getParent() != null)
				builder.setParent(options.getParent());
			if (options.isRefresh() != null)
				builder.setRefresh(options.isRefresh());
			if (options.getVersion() != null)
				builder.setVersion(options.getVersion());
			if (options.getVersionType() != null)
				builder.setVersionType(options.getVersionType());

			if (options.getPreference() != null)
				builder.setPreference(options.getPreference());
			if (!options.getFields().isEmpty()) {
				builder.setFields(options.getFields().toArray(new String[options.getFields().size()]));
			}
			if (options.isFetchSource() != null)
				builder.setFetchSource(options.isFetchSource());
			if (!options.getFetchSourceIncludes().isEmpty() || !options.getFetchSourceExcludes().isEmpty()) {
				String[] includes = options.getFetchSourceIncludes().toArray(new String[options.getFetchSourceIncludes().size()]);
				String[] excludes = options.getFetchSourceExcludes().toArray(new String[options.getFetchSourceExcludes().size()]);
				builder.setFetchSource(includes, excludes);
			}
			if (options.isTransformSource() != null)
				builder.setTransformSource(options.isTransformSource());
			if (options.isRealtime() != null)
				builder.setRealtime(options.isRealtime());
			if (options.isIgnoreErrorsOnGeneratedFields() != null) {
				builder.setIgnoreErrorsOnGeneratedFields(options.isIgnoreErrorsOnGeneratedFields());
			}
		}

		builder.execute(new ActionListener<GetResponse>() {
			@Override
			public void onResponse(GetResponse getResponse) {
				JsonObject source = (getResponse.isExists() ? new JsonObject(getResponse.getSourceAsString()) : null);
				JsonObject reply = new JsonObject()
						.put(CONST_INDEX, getResponse.getIndex())
							.put(CONST_TYPE, getResponse.getType())
							.put(CONST_ID, getResponse.getId())
							.put(CONST_VERSION, getResponse.getVersion())
							.put(CONST_SOURCE, source);
				resultHandler.handle(Future.succeededFuture(reply));
			}

			@Override
			public void onFailure(Throwable t) {
				onError("cannot get " + id, t, resultHandler);
			}
		});

	}

	@Override
	public void search(List<String> indices, SearchOptions options, Handler<AsyncResult<JsonObject>> resultHandler) {

		SearchRequestBuilder builder = client.prepareSearch(indices.toArray(new String[indices.size()]));
		applySearchOptions(options, builder);

		if (LOGGER.isDebugEnabled())
			LOGGER.debug("ElasticSearch Query using Java Client API:\n" + builder.internalBuilder());

		builder.execute(new ActionListener<SearchResponse>() {
			@Override
			public void onResponse(SearchResponse searchResponse) {
				JsonObject json = readResponse(searchResponse);
				resultHandler.handle(Future.succeededFuture(json));
			}

			@Override
			public void onFailure(Throwable t) {
				onError("cannot execute search query", t, resultHandler);
			}
		});
	}

	private void applySearchOptions(SearchOptions options, SearchRequestBuilder builder) {
		if (options != null) {
			if (!options.getTypes().isEmpty()) {
				builder.setTypes(options.getTypes().toArray(new String[options.getTypes().size()]));
			}
			if (options.getSearchType() != null)
				builder.setSearchType(options.getSearchType());
			if (options.getScroll() != null)
				builder.setScroll(options.getScroll());
			if (options.getTimeout() != null)
				builder.setTimeout(options.getTimeout());
			if (options.getTerminateAfter() != null)
				builder.setTerminateAfter(options.getTerminateAfter());
			if (options.getRouting() != null)
				builder.setRouting(options.getRouting());
			if (options.getPreference() != null)
				builder.setPreference(options.getPreference());
			if (options.getQuery() != null)
				builder.setQuery(options.getQuery().encode());
			if (options.getPostFilter() != null)
				builder.setPostFilter(options.getPostFilter().encode());
			if (options.getMinScore() != null)
				builder.setMinScore(options.getMinScore());
			if (options.getSize() != null)
				builder.setSize(options.getSize());
			if (options.getFrom() != null)
				builder.setFrom(options.getFrom());
			if (options.isExplain() != null)
				builder.setExplain(options.isExplain());
			if (options.isVersion() != null)
				builder.setVersion(options.isVersion());
			if (options.isFetchSource() != null)
				builder.setFetchSource(options.isFetchSource());
			if (!options.getFields().isEmpty())
				options.getFields().forEach(builder::addField);
			if (options.isTrackScores() != null)
				builder.setTrackScores(options.isTrackScores());
			if (options.getAggregations() != null) {
				builder.setAggregations(options.getAggregations().encode().getBytes(CHARSET_UTF8));
			}
			if (!options.getSorts().isEmpty()) {
				options.getSorts().forEach(sort -> builder.addSort(sort.getField(), sort.getOrder()));
			}
			if (options.getExtraSource() != null)
				builder.setExtraSource(options.getExtraSource().encode());
			if (options.getTemplateName() != null) {
				if (options.getTemplateType() != null) {
					Map<String, Object> params = (options.getTemplateParams() == null ? null : options.getTemplateParams().getMap());
					builder.setTemplate(new Template(options.getTemplateName(), options.getTemplateType(), null, null, params));
				} else {
					builder.setTemplate(new Template(options.getTemplateName()));
				}
			}
		}
	}

	@Override
	public void searchScroll(String scrollId, SearchScrollOptions options, Handler<AsyncResult<JsonObject>> resultHandler) {

		SearchScrollRequestBuilder builder = client.prepareSearchScroll(scrollId);

		if (options != null) {
			if (options.getScroll() != null)
				builder.setScroll(options.getScroll());
		}

		builder.execute(new ActionListener<SearchResponse>() {
			@Override
			public void onResponse(SearchResponse searchResponse) {
				JsonObject json = readResponse(searchResponse);
				resultHandler.handle(Future.succeededFuture(json));
			}

			@Override
			public void onFailure(Throwable t) {
				onError("", t, resultHandler);
			}
		});

	}

	@Override
	public void delete(DeleteOptions options, Handler<AsyncResult<JsonObject>> resultHandler) {
		DeleteRequestBuilder builder = convert(options);

		builder.execute(new ActionListener<DeleteResponse>() {
			@Override
			public void onResponse(DeleteResponse deleteResponse) {
				JsonObject json = new JsonObject()
						.put(CONST_INDEX, deleteResponse.getIndex())
							.put(CONST_TYPE, deleteResponse.getType())
							.put(CONST_ID, deleteResponse.getId())
							.put(CONST_VERSION, deleteResponse.getVersion());
				resultHandler.handle(Future.succeededFuture(json));
			}

			@Override
			public void onFailure(Throwable t) {
				onError("cannot delete " + options.getId(), t, resultHandler);
			}
		});

	}

	private DeleteRequestBuilder convert(DeleteOptions options) {
		DeleteRequestBuilder builder = client.prepareDelete(options.getIndex(), options.getType(), options.getId());

		if (options != null) {
			if (options.getRouting() != null)
				builder.setRouting(options.getRouting());
			if (options.getParent() != null)
				builder.setParent(options.getParent());
			if (options.isRefresh() != null)
				builder.setRefresh(options.isRefresh());
			if (options.getConsistencyLevel() != null)
				builder.setConsistencyLevel(options.getConsistencyLevel());
			if (options.getVersion() != null)
				builder.setVersion(options.getVersion());
			if (options.getVersionType() != null)
				builder.setVersionType(options.getVersionType());
			if (options.getTimeout() != null)
				builder.setTimeout(options.getTimeout());
		}

		return builder;
	}

	@Override
	public void suggest(String index, SuggestOptions options, Handler<AsyncResult<JsonObject>> resultHandler) {

		final SuggestRequestBuilder builder = client.prepareSuggest(index);

		if (options != null) {
			if (options.getName() != null) {
				final CompletionSuggestionBuilder completionBuilder = new CompletionSuggestionBuilder(options.getName());
				if (options.getText() != null) {
					completionBuilder.text(options.getText());
				}
				if (options.getField() != null) {
					completionBuilder.field(options.getField());
				}

				builder.addSuggestion(completionBuilder);
			}
		}

		builder.execute(new ActionListener<SuggestResponse>() {

			@Override
			public void onResponse(SuggestResponse suggestResponse) {
				JsonObject json = readResponse(suggestResponse.getSuggest());
				resultHandler.handle(Future.succeededFuture(json));
			}

			@Override
			public void onFailure(Throwable t) {
				onError("cannot suggest", t, resultHandler);
			}
		});

	}

	@Override
	public TransportClient getClient() {
		return client;
	}

	protected JsonObject readResponse(ToXContent toXContent) {

		try {
			XContentBuilder builder = XContentFactory.jsonBuilder();
			builder.startObject();
			toXContent.toXContent(builder, SearchResponse.EMPTY_PARAMS);
			builder.endObject();

			return new JsonObject(builder.string());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public static void onError(String message, Throwable t, boolean logIt, Handler<AsyncResult<JsonObject>> resultHandler) {
		if (logIt)
			LOGGER.warn(message, t);
		if (t instanceof RemoteTransportException) {
			RemoteTransportException rt = (RemoteTransportException) t;
			t = rt.getCause();
		}
		resultHandler.handle(Future.failedFuture(t));
	}

	public ElasticSearchConfigurator getConfigurator() {
		return configurator;
	}

	@Override
	public void onError(String message, Throwable t, Handler<AsyncResult<JsonObject>> resultHandler) {
		onError(message, t, configurator.isLogging(), resultHandler);
	}

	@Override
	public void mget(MultiGetRequest docs, Handler<AsyncResult<JsonObject>> resultHandler) {
		MultiGetRequestBuilder builder = client.prepareMultiGet();
		for (Entry<GetKey, Set<String>> entry : docs.getData().entrySet()) {
			GetKey key = entry.getKey();
			builder.add(key.getIndex(), key.getType(), entry.getValue().toArray(new String[0]));
		}
		builder.execute(new ActionListener<MultiGetResponse>() {
			@Override
			public void onResponse(MultiGetResponse multiGetResponse) {
				JsonArray docs = new JsonArray();
				for (MultiGetItemResponse multiGetItem : multiGetResponse.getResponses()) {
					GetResponse getResponse = multiGetItem.getResponse();
					JsonObject reply = new JsonObject();
					if (multiGetItem.getFailure() != null) {
						MultiGetResponse.Failure failure = multiGetItem.getFailure();
						reply.put("failure", true);
						reply.put("failureMessage", failure.getMessage());
					}
					JsonObject source = (getResponse.isExists() ? new JsonObject(getResponse.getSourceAsString()) : null);
					reply
							.put(CONST_INDEX, getResponse.getIndex())
								.put(CONST_TYPE, getResponse.getType())
								.put(CONST_ID, getResponse.getId())
								.put(CONST_VERSION, getResponse.getVersion())
								.put(CONST_SOURCE, source);
					docs.add(reply);
				}
				JsonObject reply = new JsonObject()
						.put("docs", docs);
				resultHandler.handle(Future.succeededFuture(reply));
			}

			@Override
			public void onFailure(Throwable t) {
				onError("cannot get " + docs, t, resultHandler);
			}
		});
	}

}
