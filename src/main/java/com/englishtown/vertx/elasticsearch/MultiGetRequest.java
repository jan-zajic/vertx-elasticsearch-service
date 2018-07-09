package com.englishtown.vertx.elasticsearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@DataObject
public class MultiGetRequest {

	private Map<GetKey, Set<String>> data;

	public MultiGetRequest() {
		this.data = new HashMap<>();
	}

	@SuppressWarnings("unchecked")
	public MultiGetRequest(JsonObject obj) {
		this.data = new HashMap<>();
		for (Entry<String, Object> entry : obj) {
			String index = entry.getKey();
			JsonObject value = (JsonObject) entry.getValue();
			for (Entry<String, Object> subEntry : value) {
				String type = subEntry.getKey();
				JsonArray ids = (JsonArray) subEntry.getValue();
				data.put(new GetKey(index, type), new HashSet<>(ids.getList()));
			}
		}
	}
	
	public Map<GetKey, Set<String>> getData() {
		return data;
	}

	public void setData(Map<GetKey, Set<String>> data) {
		this.data = data;
	}
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		for (Entry<GetKey, Set<String>> entry : data.entrySet()) {
			JsonObject typeMap = json.getJsonObject(entry.getKey().getIndex(), new JsonObject());
			json.put(entry.getKey().getIndex(), typeMap);
			JsonArray idList = typeMap.getJsonArray(entry.getKey().getType(), new JsonArray());
			typeMap.put(entry.getKey().getType(), idList);
			idList.addAll(new JsonArray(new ArrayList<>(entry.getValue())));			
		}
		return json;
	}

	public void add(GetKey key, String value) {
		Set<String> idList = data.computeIfAbsent(key, k -> new HashSet<>());
		idList.add(value);
	}
	
}
