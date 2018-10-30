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

/** @module vertx-elasticsearch-service-js/elastic_search_admin_service */
var utils = require('vertx-js/util/utils');
var Vertx = require('vertx-js/vertx');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JElasticSearchAdminService = Java.type('com.englishtown.vertx.elasticsearch.ElasticSearchAdminService');
var MappingOptions = Java.type('com.englishtown.vertx.elasticsearch.MappingOptions');

/**
 Admin service

 @class
*/
var ElasticSearchAdminService = function(j_val) {

  var j_elasticSearchAdminService = j_val;
  var that = this;

  /**

   @public
   @param indices {Array.<string>} 
   @param type {string} 
   @param source {Object} 
   @param options {Object} 
   @param resultHandler {function} 
   */
  this.putMapping = function(indices, type, source, options, resultHandler) {
    var __args = arguments;
    if (__args.length === 5 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'string' && (typeof __args[2] === 'object' && __args[2] != null) && (typeof __args[3] === 'object' && __args[3] != null) && typeof __args[4] === 'function') {
      j_elasticSearchAdminService["putMapping(java.util.List,java.lang.String,io.vertx.core.json.JsonObject,com.englishtown.vertx.elasticsearch.MappingOptions,io.vertx.core.Handler)"](utils.convParamListBasicOther(indices), type, utils.convParamJsonObject(source), options != null ? new MappingOptions(new JsonObject(Java.asJSONCompatible(options))) : null, function(ar) {
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
   @param index {string} 
   @param settings {Object} 
   @param mappings {Object} 
   @param resultHandler {function} 
   */
  this.createIndex = function(index, settings, mappings, resultHandler) {
    var __args = arguments;
    if (__args.length === 4 && typeof __args[0] === 'string' && (typeof __args[1] === 'object' && __args[1] != null) && (typeof __args[2] === 'object' && __args[2] != null) && typeof __args[3] === 'function') {
      j_elasticSearchAdminService["createIndex(java.lang.String,io.vertx.core.json.JsonObject,io.vertx.core.json.JsonObject,io.vertx.core.Handler)"](index, utils.convParamJsonObject(settings), utils.convParamJsonObject(mappings), function(ar) {
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
   @param index {string} 
   @param resultHandler {function} 
   */
  this.deleteIndex = function(index, resultHandler) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'string' && typeof __args[1] === 'function') {
      j_elasticSearchAdminService["deleteIndex(java.lang.String,io.vertx.core.Handler)"](index, function(ar) {
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
  this._jdel = j_elasticSearchAdminService;
};

ElasticSearchAdminService._jclass = utils.getJavaClass("com.englishtown.vertx.elasticsearch.ElasticSearchAdminService");
ElasticSearchAdminService._jtype = {
  accept: function(obj) {
    return ElasticSearchAdminService._jclass.isInstance(obj._jdel);
  },
  wrap: function(jdel) {
    var obj = Object.create(ElasticSearchAdminService.prototype, {});
    ElasticSearchAdminService.apply(obj, arguments);
    return obj;
  },
  unwrap: function(obj) {
    return obj._jdel;
  }
};
ElasticSearchAdminService._create = function(jdel) {
  var obj = Object.create(ElasticSearchAdminService.prototype, {});
  ElasticSearchAdminService.apply(obj, arguments);
  return obj;
}
/**

 @memberof module:vertx-elasticsearch-service-js/elastic_search_admin_service
 @param vertx {Vertx} 
 @param address {string} 
 @return {ElasticSearchAdminService}
 */
ElasticSearchAdminService.createEventBusProxy = function(vertx, address) {
  var __args = arguments;
  if (__args.length === 2 && typeof __args[0] === 'object' && __args[0]._jdel && typeof __args[1] === 'string') {
    return utils.convReturnVertxGen(ElasticSearchAdminService, JElasticSearchAdminService["createEventBusProxy(io.vertx.core.Vertx,java.lang.String)"](vertx._jdel, address));
  } else throw new TypeError('function invoked with invalid arguments');
};

module.exports = ElasticSearchAdminService;