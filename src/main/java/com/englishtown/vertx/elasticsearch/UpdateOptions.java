package com.englishtown.vertx.elasticsearch;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.elasticsearch.script.ScriptService;

import java.util.ArrayList;
import java.util.List;

/**
 * Update operation options
 */
@DataObject
public class UpdateOptions extends AbstractWriteOptions<UpdateOptions> {

	private String index;
	private String type;
	private String id;
	
    private String script;
    private ScriptService.ScriptType scriptType;
    private String scriptLang;
    private JsonObject scriptParams;
    private List<String> fields = new ArrayList<>();
    private Integer retryOnConflict;
    private JsonObject doc;
    private JsonObject upsert;
    private Boolean docAsUpsert;
    private Boolean detectNoop;
    private Boolean scriptedUpsert;

    public static final String FIELD_INDEX = "index";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_ID = "id";
    
    public static final String FIELD_SCRIPT = "script";
    public static final String FIELD_SCRIPT_TYPE = "scriptType";
    public static final String FIELD_SCRIPT_LANG = "scriptLang";
    public static final String FIELD_SCRIPT_PARAMS = "scriptParams";
    public static final String FIELD_FIELDS = "fields";
    public static final String FIELD_RETRY_ON_CONFLICT = "retryOnConflict";
    public static final String FIELD_DOC = "doc";
    public static final String FIELD_UPSERT = "upsert";
    public static final String FIELD_DOC_AS_UPSERT = "docAsUpsert";
    public static final String FIELD_DETECT_NOOP = "detectNoop";
    public static final String FIELD_SCRIPTED_UPSERT = "scriptedUpsert";

    static final String SCRIPT_TYPE_INLINE = "inline";
    static final String SCRIPT_TYPE_INDEXED = "indexed";
    static final String SCRIPT_TYPE_FILE = "file";

    public UpdateOptions() {
    }

    public UpdateOptions(UpdateOptions other) {
        super(other);
        index = other.getIndex();
        type = other.getType();
        id = other.getId();
        
        script = other.getScript();
        scriptType = other.getScriptType();
        scriptLang = other.getScriptLang();
        scriptParams = other.getScriptParams();
        fields.addAll(other.getFields());
        retryOnConflict = other.getRetryOnConflict();
        doc = other.getDoc();
        upsert = other.getUpsert();
        docAsUpsert = other.isDocAsUpsert();
        detectNoop = other.isDetectNoop();
        scriptedUpsert = other.isScriptedUpsert();
    }

    @SuppressWarnings("unchecked")
    public UpdateOptions(JsonObject json) {
        super(json);
        index = json.getString(FIELD_INDEX);
        type = json.getString(FIELD_TYPE);
        id = json.getString(FIELD_ID);
        
        script = json.getString(FIELD_SCRIPT);
        scriptLang = json.getString(FIELD_SCRIPT_LANG);
        scriptParams = json.getJsonObject(FIELD_SCRIPT_PARAMS);
        //noinspection unchecked
        fields = json.getJsonArray(FIELD_FIELDS, new JsonArray()).getList();
        retryOnConflict = json.getInteger(FIELD_RETRY_ON_CONFLICT);
        doc = json.getJsonObject(FIELD_DOC);
        upsert = json.getJsonObject(FIELD_UPSERT);
        docAsUpsert = json.getBoolean(FIELD_DOC_AS_UPSERT);
        detectNoop = json.getBoolean(FIELD_DETECT_NOOP);
        scriptedUpsert = json.getBoolean(FIELD_SCRIPTED_UPSERT);

        String s = json.getString(FIELD_SCRIPT_TYPE);
        if (s != null) {
            switch (s) {
                case SCRIPT_TYPE_INLINE:
                    scriptType = ScriptService.ScriptType.INLINE;
                    break;
                case SCRIPT_TYPE_INDEXED:
                    scriptType = ScriptService.ScriptType.INDEXED;
                    break;
                case SCRIPT_TYPE_FILE:
                    scriptType = ScriptService.ScriptType.FILE;
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported script type: " + s);
            }
        }
    }

    public String getScript() {
        return script;
    }

    public UpdateOptions setScript(String script, ScriptService.ScriptType scriptType) {
        this.script = script;
        this.scriptType = scriptType;
        return this;
    }
    
    public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ScriptService.ScriptType getScriptType() {
        return scriptType;
    }

    public String getScriptLang() {
        return scriptLang;
    }

    public UpdateOptions setScriptLang(String scriptLang) {
        this.scriptLang = scriptLang;
        return this;
    }

    public JsonObject getScriptParams() {
        return scriptParams;
    }

    public UpdateOptions setScriptParams(JsonObject scriptParams) {
        this.scriptParams = scriptParams;
        return this;
    }

    public List<String> getFields() {
        return fields;
    }

    public UpdateOptions addField(String field) {
        fields.add(field);
        return this;
    }

    public Integer getRetryOnConflict() {
        return retryOnConflict;
    }

    public UpdateOptions setRetryOnConflict(Integer retryOnConflict) {
        this.retryOnConflict = retryOnConflict;
        return this;
    }

    public JsonObject getDoc() {
        return doc;
    }

    public UpdateOptions setDoc(JsonObject doc) {
        this.doc = doc;
        return this;
    }

    public JsonObject getUpsert() {
        return upsert;
    }

    public UpdateOptions setUpsert(JsonObject upsert) {
        this.upsert = upsert;
        return this;
    }

    public Boolean isDocAsUpsert() {
        return docAsUpsert;
    }

    public UpdateOptions setDocAsUpsert(Boolean docAsUpsert) {
        this.docAsUpsert = docAsUpsert;
        return this;
    }

    public Boolean isDetectNoop() {
        return detectNoop;
    }

    public UpdateOptions setDetectNoop(Boolean detectNoop) {
        this.detectNoop = detectNoop;
        return this;
    }

    public Boolean isScriptedUpsert() {
        return scriptedUpsert;
    }

    public UpdateOptions setScriptedUpsert(Boolean scriptedUpsert) {
        this.scriptedUpsert = scriptedUpsert;
        return this;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = super.toJson();

        if(getIndex() != null) json.put(FIELD_INDEX, getIndex());
        if(getType() != null) json.put(FIELD_TYPE, getType());
        if(getId() != null) json.put(FIELD_ID, getId());
        
        if (getScript() != null) json.put(FIELD_SCRIPT, getScript());
        if (getScriptLang() != null) json.put(FIELD_SCRIPT_LANG, getScriptLang());
        if (getScriptParams() != null) json.put(FIELD_SCRIPT_PARAMS, getScriptParams());
        if (!getFields().isEmpty()) json.put(FIELD_FIELDS, new JsonArray(getFields()));
        if (getRetryOnConflict() != null) json.put(FIELD_RETRY_ON_CONFLICT, getRetryOnConflict());
        if (getDoc() != null) json.put(FIELD_DOC, getDoc());
        if (getUpsert() != null) json.put(FIELD_UPSERT, getUpsert());
        if (isDocAsUpsert() != null) json.put(FIELD_DOC_AS_UPSERT, isDocAsUpsert());
        if (isDetectNoop() != null) json.put(FIELD_DETECT_NOOP, isDetectNoop());
        if (isScriptedUpsert() != null) json.put(FIELD_SCRIPTED_UPSERT, isScriptedUpsert());
        if (getScriptType() != null) json.put(FIELD_SCRIPT_TYPE, getScriptType().toString().toLowerCase());

        return json;
    }
}
