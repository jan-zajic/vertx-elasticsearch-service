package com.englishtown.vertx.elasticsearch;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Delete operation options
 */
@DataObject
public class DeleteOptions extends AbstractWriteOptions<DeleteOptions> {

	private String index;
	private String type;
	private String id;
	
    public static final String FIELD_INDEX = "index";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_ID = "id";
	
    public DeleteOptions() {
    }

    public DeleteOptions(DeleteOptions other) {
        super(other);

        index = other.getIndex();
        type = other.getType();
        id = other.getId();
    }

    public DeleteOptions(JsonObject json) {
        super(json);
        
        index = json.getString(FIELD_INDEX);
        type = json.getString(FIELD_TYPE);
        id = json.getString(FIELD_ID);
    }

    public String getIndex() {
		return index;
	}

	public DeleteOptions setIndex(String index) {
		this.index = index;
		return this;
	}

	public String getType() {
		return type;
	}

	public DeleteOptions setType(String type) {
		this.type = type;
		return this;
	}

	public String getId() {
		return id;
	}

	public DeleteOptions setId(String id) {
		this.id = id;
		return this;
	}

	@Override
    public JsonObject toJson() {
    	JsonObject json = super.toJson();
            	
        if(getIndex() != null) json.put(FIELD_INDEX, getIndex());
        if(getType() != null) json.put(FIELD_TYPE, getType());
        if(getId() != null) json.put(FIELD_ID, getId());
        
        return json;
    }
}
