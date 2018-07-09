package com.englishtown.vertx.elasticsearch;

import java.util.ArrayList;
import java.util.List;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * Bulk operation options
 */
@DataObject
@SuppressWarnings("rawtypes")
public class BulkOptions extends AbstractWriteOptions<BulkOptions> {

	private List<AbstractWriteOptions> writeOptions = new ArrayList<>();
	
	public BulkOptions() {
	}
	
	public BulkOptions(BulkOptions other) {
		super(other);
		this.writeOptions = other.writeOptions;
	}
	
	public BulkOptions(JsonObject json) {
		super(json);
		if(json.containsKey("writeOptions")) {
			JsonArray array = json.getJsonArray("writeOptions");
			for(int i = 0; i < array.size(); i++) {
				JsonObject object = array.getJsonObject(i);
				String objectType = object.getString("writeOptionsType");
				switch(objectType) {
					case "UpdateOptions":
						writeOptions.add(new UpdateOptions(object));
						break;
					case "DeleteOptions":
						writeOptions.add(new DeleteOptions(object));
						break;
					case "IndexOptions":
						writeOptions.add(new IndexOptions(object));
						break;
				}
			}
		}
	}

	public List<AbstractWriteOptions> getWriteOptions() {
		return writeOptions;
	}

	public void setWriteOptions(List<AbstractWriteOptions> writeOptions) {
		this.writeOptions = writeOptions;
	}
	
	public JsonObject toJson() {
		JsonObject json = super.toJson();
		JsonArray array = new JsonArray();
		for (AbstractWriteOptions abstractWriteOptions : writeOptions) {
			array.add(abstractWriteOptions.toJson());		
		}
		json.put("writeOptions", array);
		return json;
	}
}
