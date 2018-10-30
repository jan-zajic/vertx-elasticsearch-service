/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

/** @module vertx-elasticsearch-service-js/elastic_search_service */
var utils = require('vertx-js/util/utils');
var Vertx = require('vertx-js/vertx');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JElasticSearchService = Java.type('com.englishtown.vertx.elasticsearch.ElasticSearchService');
var MultiGetRequest = Java.type('com.englishtown.vertx.elasticsearch.MultiGetRequest');
var DeleteOptions = Java.type('com.englishtown.vertx.elasticsearch.DeleteOptions');
var UpdateOptions = Java.type('com.englishtown.vertx.elasticsearch.UpdateOptions');
var UpdateByQueryOptions = Java.type('com.englishtown.vertx.elasticsearch.UpdateByQueryOptions');
var SearchOptions = Java.type('com.englishtown.vertx.elasticsearch.SearchOptions');
var IndexOptions = Java.type('com.englishtown.vertx.elasticsearch.IndexOptions');
var SuggestOptions = Java.type('com.englishtown.vertx.elasticsearch.SuggestOptions');
var BulkOptions = Java.type('com.englishtown.vertx.elasticsearch.BulkOptions');
var GetOptions = Java.type('com.englishtown.vertx.elasticsearch.GetOptions');
var SearchScrollOptions = Java.type('com.englishtown.vertx.elasticsearch.SearchScrollOptions');

/**
 ElasticSearch service

 @class
*/
var ElasticSearchService = function(j_val) {

  var j_elasticSearchService = j_val;
  var that = this;

  /**

   @public

   */
  this.start = function() {
    var __args = arguments;
    if (__args.length === 0) {
      j_elasticSearchService["start()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public

   */
  this.stop = function() {
    var __args = arguments;
    if (__args.length === 0) {
      j_elasticSearchService["stop()"]();
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   http://www.elastic.co/guide/en/elasticsearch/client/java-api/1.4/index_.html

   @public
   @param options {Object} optional index options (id, timeout, ttl, etc.) 
   @param resultHandler {function} result handler callback 
   */
  this.index = function(options, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_elasticSearchService["index(com.englishtown.vertx.elasticsearch.IndexOptions,io.vertx.core.Handler)"](options != null ? new IndexOptions(new JsonObject(Java.asJSONCompatible(options))) : null, function(ar) {
      if (ar.succeeded()) {
        resultHandler(utils.convReturnJson(ar.result()), null);
      } else {
        resultHandler(null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   http://www.elastic.co/guide/en/elasticsearch/client/java-api/1.4/java-update-api.html

   @public
   @param options {Object} the update options (doc, script, etc.) 
   @param resultHandler {function} result handler callback 
   */
  this.update = function(options, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_elasticSearchService["update(com.englishtown.vertx.elasticsearch.UpdateOptions,io.vertx.core.Handler)"](options != null ? new UpdateOptions(new JsonObject(Java.asJSONCompatible(options))) : null, function(ar) {
      if (ar.succeeded()) {
        resultHandler(utils.convReturnJson(ar.result()), null);
      } else {
        resultHandler(null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-docs-bulk.html

   @public
   @param options {Object} the bulk options 
   @param resultHandler {function} result handler callback 
   */
  this.bulk = function(options, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_elasticSearchService["bulk(com.englishtown.vertx.elasticsearch.BulkOptions,io.vertx.core.Handler)"](options != null ? new BulkOptions(new JsonObject(Java.asJSONCompatible(options))) : null, function(ar) {
      if (ar.succeeded()) {
        resultHandler(utils.convReturnJson(ar.result()), null);
      } else {
        resultHandler(null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   http://www.elastic.co/guide/en/elasticsearch/client/java-api/1.4/get.html

   @public
   @param index {string} the index name 
   @param type {string} the type name 
   @param id {string} the source id to update 
   @param options {Object} the update options 
   @param resultHandler {function} result handler callback 
   */
  this.get = function(index, type, id, options, resultHandler) {
    var __args = arguments;
    if (__args.length === 5 && typeof __args[0] === 'string' && typeof __args[1] === 'string' && typeof __args[2] === 'string' && (typeof __args[3] === 'object' && __args[3] != null) && typeof __args[4] === 'function') {
      j_elasticSearchService["get(java.lang.String,java.lang.String,java.lang.String,com.englishtown.vertx.elasticsearch.GetOptions,io.vertx.core.Handler)"](index, type, id, options != null ? new GetOptions(new JsonObject(Java.asJSONCompatible(options))) : null, function(ar) {
      if (ar.succeeded()) {
        resultHandler(utils.convReturnJson(ar.result()), null);
      } else {
        resultHandler(null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   http://www.elastic.co/guide/en/elasticsearch/client/java-api/1.4/multi-get.html

   @public
   @param docs {Object} documens to fetch 
   @param resultHandler {function} result handler callback 
   */
  this.mget = function(docs, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_elasticSearchService["mget(com.englishtown.vertx.elasticsearch.MultiGetRequest,io.vertx.core.Handler)"](docs != null ? new MultiGetRequest(new JsonObject(Java.asJSONCompatible(docs))) : null, function(ar) {
      if (ar.succeeded()) {
        resultHandler(utils.convReturnJson(ar.result()), null);
      } else {
        resultHandler(null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @public
   @param indices {Array.<string>} 
   @param options {Object} 
   @param resultHandler {function} 
   */
  this.search = function(indices, options, resultHandler) {
    var __args = arguments;
    if (__args.length === 3 && typeof __args[0] === 'object' && __args[0] instanceof Array && (typeof __args[1] === 'object' && __args[1] != null) && typeof __args[2] === 'function') {
      j_elasticSearchService["search(java.util.List,com.englishtown.vertx.elasticsearch.SearchOptions,io.vertx.core.Handler)"](utils.convParamListBasicOther(indices), options != null ? new SearchOptions(new JsonObject(Java.asJSONCompatible(options))) : null, function(ar) {
      if (ar.succeeded()) {
        resultHandler(utils.convReturnJson(ar.result()), null);
      } else {
        resultHandler(null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   http://www.elastic.co/guide/en/elasticsearch/reference/1.4/search-request-scroll.html

   @public
   @param scrollId {string} 
   @param options {Object} 
   @param resultHandler {function} 
   */
  this.searchScroll = function(scrollId, options, resultHandler) {
    var __args = arguments;
    if (__args.length === 3 && typeof __args[0] === 'string' && (typeof __args[1] === 'object' && __args[1] != null) && typeof __args[2] === 'function') {
      j_elasticSearchService["searchScroll(java.lang.String,com.englishtown.vertx.elasticsearch.SearchScrollOptions,io.vertx.core.Handler)"](scrollId, options != null ? new SearchScrollOptions(new JsonObject(Java.asJSONCompatible(options))) : null, function(ar) {
      if (ar.succeeded()) {
        resultHandler(utils.convReturnJson(ar.result()), null);
      } else {
        resultHandler(null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   http://www.elastic.co/guide/en/elasticsearch/client/java-api/1.4/delete.html

   @public
   @param options {Object} optional delete options (timeout, etc.) 
   @param resultHandler {function} result handler callback 
   */
  this.delete = function(options, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && (typeof __args[0] === 'object' && __args[0] != null) && typeof __args[1] === 'function') {
      j_elasticSearchService["delete(com.englishtown.vertx.elasticsearch.DeleteOptions,io.vertx.core.Handler)"](options != null ? new DeleteOptions(new JsonObject(Java.asJSONCompatible(options))) : null, function(ar) {
      if (ar.succeeded()) {
        resultHandler(utils.convReturnJson(ar.result()), null);
      } else {
        resultHandler(null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   https://www.elastic.co/guide/en/elasticsearch/reference/current/search-suggesters.html

   @public
   @param index {string} the index name 
   @param options {Object} optional suggest options 
   @param resultHandler {function} result handler callback 
   */
  this.suggest = function(index, options, resultHandler) {
    var __args = arguments;
    if (__args.length === 3 && typeof __args[0] === 'string' && (typeof __args[1] === 'object' && __args[1] != null) && typeof __args[2] === 'function') {
      j_elasticSearchService["suggest(java.lang.String,com.englishtown.vertx.elasticsearch.SuggestOptions,io.vertx.core.Handler)"](index, options != null ? new SuggestOptions(new JsonObject(Java.asJSONCompatible(options))) : null, function(ar) {
      if (ar.succeeded()) {
        resultHandler(utils.convReturnJson(ar.result()), null);
      } else {
        resultHandler(null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**
   https://www.elastic.co/guide/en/elasticsearch/reference/2.3/docs-update-by-query.html

   @public
   @param indices {Array.<string>} optional the indices names 
   @param options {Object} suggest options 
   @param resultHandler {function} result handler callback 
   */
  this.updateByQuery = function(indices, options, resultHandler) {
    var __args = arguments;
    if (__args.length === 3 && typeof __args[0] === 'object' && __args[0] instanceof Array && (typeof __args[1] === 'object' && __args[1] != null) && typeof __args[2] === 'function') {
      j_elasticSearchService["updateByQuery(java.util.List,com.englishtown.vertx.elasticsearch.UpdateByQueryOptions,io.vertx.core.Handler)"](utils.convParamListBasicOther(indices), options != null ? new UpdateByQueryOptions(new JsonObject(Java.asJSONCompatible(options))) : null, function(ar) {
      if (ar.succeeded()) {
        resultHandler(utils.convReturnJson(ar.result()), null);
      } else {
        resultHandler(null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_elasticSearchService;
};

ElasticSearchService._jclass = utils.getJavaClass("com.englishtown.vertx.elasticsearch.ElasticSearchService");
ElasticSearchService._jtype = {
  accept: function(obj) {
    return ElasticSearchService._jclass.isInstance(obj._jdel);
  },
  wrap: function(jdel) {
    var obj = Object.create(ElasticSearchService.prototype, {});
    ElasticSearchService.apply(obj, arguments);
    return obj;
  },
  unwrap: function(obj) {
    return obj._jdel;
  }
};
ElasticSearchService._create = function(jdel) {
  var obj = Object.create(ElasticSearchService.prototype, {});
  ElasticSearchService.apply(obj, arguments);
  return obj;
}
/**

 @memberof module:vertx-elasticsearch-service-js/elastic_search_service
 @param vertx {Vertx} 
 @param address {string} 
 @return {ElasticSearchService}
 */
ElasticSearchService.createEventBusProxy = function(vertx, address) {
  var __args = arguments;
  if (__args.length === 2 && typeof __args[0] === 'object' && __args[0]._jdel && typeof __args[1] === 'string') {
    return utils.convReturnVertxGen(ElasticSearchService, JElasticSearchService["createEventBusProxy(io.vertx.core.Vertx,java.lang.String)"](vertx._jdel, address));
  } else throw new TypeError('function invoked with invalid arguments');
};

module.exports = ElasticSearchService;