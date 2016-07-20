package com.englishtown.vertx.elasticsearch;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import org.elasticsearch.action.index.IndexRequest;

/**
 * Index operation options
 */
@DataObject
public class IndexOptions extends AbstractWriteOptions<IndexOptions> {

	private String index;
	private String type;
	private JsonObject doc;
	
    private String id;
    private IndexRequest.OpType opType;
    private String timestamp;
    private Long ttl;

    public static final String FIELD_INDEX = "index";
    public static final String FIELD_TYPE = "type";
    
    public static final String FIELD_ID = "id";
    public static final String FIELD_OP_TYPE = "opType";
    public static final String FIELD_TIMESTAMP = "timestamp";
    public static final String FIELD_TTL = "ttl";
    public static final String FIELD_DOC = "doc";

    public IndexOptions() {
    }

    public IndexOptions(IndexOptions other) {
        super(other);

        index = other.getIndex();
        type = other.getType();
        
        doc = other.getDoc();
        
        id = other.getId();
        opType = other.getOpType();
        timestamp = other.getTimestamp();
        ttl = other.getTtl();

    }

    public IndexOptions(JsonObject json) {
        super(json);

        index = json.getString(FIELD_INDEX);
        type = json.getString(FIELD_TYPE);
        
        doc = json.getJsonObject(FIELD_DOC);
        
        id = json.getString(FIELD_ID);
        timestamp = json.getString(FIELD_TIMESTAMP);
        ttl = json.getLong(FIELD_TTL);

        String s = json.getString(FIELD_OP_TYPE);
        if (s != null) opType = IndexRequest.OpType.fromString(s);
    }

    public String getId() {
        return id;
    }

    public IndexOptions setId(String id) {
        this.id = id;
        return this;
    }

    public String getIndex() {
		return index;
	}

	public IndexOptions setIndex(String index) {
		this.index = index;
		return this;
	}

	public String getType() {
		return type;
	}

	public IndexOptions setType(String type) {
		this.type = type;
		return this;
	}
	
	public JsonObject getDoc() {
		return doc;
	}

	public IndexOptions setDoc(JsonObject doc) {
		this.doc = doc;
		return this;
	}

	public IndexRequest.OpType getOpType() {
        return opType;
    }

    public IndexOptions setOpType(IndexRequest.OpType opType) {
        this.opType = opType;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public IndexOptions setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Long getTtl() {
        return ttl;
    }

    public IndexOptions setTtl(Long ttl) {
        this.ttl = ttl;
        return this;
    }

    public JsonObject toJson() {
        JsonObject json = super.toJson();
        
        if(getIndex() != null) json.put(FIELD_INDEX, getIndex());
        if(getType() != null) json.put(FIELD_TYPE, getType());
        
        if (getId() != null) json.put(FIELD_ID, getId());
        if (getOpType() != null) json.put(FIELD_OP_TYPE, getOpType().toString().toLowerCase());
        if (getTimestamp() != null) json.put(FIELD_TIMESTAMP, getTimestamp());
        if (getTtl() != null) json.put(FIELD_TTL, getTtl());

        if (getDoc() != null) json.put(FIELD_DOC, getDoc());
        
        return json;
    }

}
