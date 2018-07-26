package com.englishtown.vertx.elasticsearch;

import org.elasticsearch.action.WriteConsistencyLevel;
import org.elasticsearch.script.ScriptService;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Update by query options
 */
@DataObject
public class UpdateByQueryOptions extends SearchOptions {

	public static final String FIELD_ABORT_ON_VERSION_CONFLICT = "abortOnVersionConflict";
	
	private String script;
  private ScriptService.ScriptType scriptType;
  private String scriptLang;
  private JsonObject scriptParams;
  private Boolean abortOnVersionConflict;
  private WriteConsistencyLevel consistencyLevel;
  private Boolean refresh;
  
	public UpdateByQueryOptions() {
		super();
	}
	
	public UpdateByQueryOptions(JsonObject json) {
		super(json);
		script = json.getString(UpdateOptions.FIELD_SCRIPT);
    scriptLang = json.getString(UpdateOptions.FIELD_SCRIPT_LANG);
    scriptParams = json.getJsonObject(UpdateOptions.FIELD_SCRIPT_PARAMS);
    
    String s = json.getString(UpdateOptions.FIELD_SCRIPT_TYPE);
    if (s != null) {
        switch (s) {
            case UpdateOptions.SCRIPT_TYPE_INLINE:
                scriptType = ScriptService.ScriptType.INLINE;
                break;
            case UpdateOptions.SCRIPT_TYPE_INDEXED:
                scriptType = ScriptService.ScriptType.INDEXED;
                break;
            case UpdateOptions.SCRIPT_TYPE_FILE:
                scriptType = ScriptService.ScriptType.FILE;
                break;
            default:
                throw new IllegalArgumentException("Unsupported script type: " + s);
        }
    }
    
    abortOnVersionConflict = json.getBoolean(FIELD_ABORT_ON_VERSION_CONFLICT);
    refresh = json.getBoolean(AbstractOptions.FIELD_REFRESH);
    
    String c = json.getString(AbstractWriteOptions.FIELD_CONSISTENCY_LEVEL);
    if (c != null) consistencyLevel = WriteConsistencyLevel.fromString(c);
	}
	
	public UpdateByQueryOptions(UpdateByQueryOptions other) {
		super(other);
		script = other.getScript();
    scriptType = other.getScriptType();
    scriptLang = other.getScriptLang();
    scriptParams = other.getScriptParams();
    abortOnVersionConflict = other.getAbortOnVersionConflict();
    consistencyLevel = other.getConsistencyLevel();
    refresh = other.getRefresh();
	}
	
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public ScriptService.ScriptType getScriptType() {
		return scriptType;
	}
	public void setScriptType(ScriptService.ScriptType scriptType) {
		this.scriptType = scriptType;
	}
	public String getScriptLang() {
		return scriptLang;
	}
	public void setScriptLang(String scriptLang) {
		this.scriptLang = scriptLang;
	}
	public JsonObject getScriptParams() {
		return scriptParams;
	}
	public void setScriptParams(JsonObject scriptParams) {
		this.scriptParams = scriptParams;
	}
	public UpdateByQueryOptions setScript(String script, ScriptService.ScriptType scriptType) {
    this.script = script;
    this.scriptType = scriptType;
    return this;
	}
	public Boolean getAbortOnVersionConflict() {
		return abortOnVersionConflict;
	}
	public void setAbortOnVersionConflict(Boolean abortOnVersionConflict) {
		this.abortOnVersionConflict = abortOnVersionConflict;
	}
	public WriteConsistencyLevel getConsistencyLevel() {
		return consistencyLevel;
	}
	public void setConsistencyLevel(WriteConsistencyLevel consistencyLevel) {
		this.consistencyLevel = consistencyLevel;
	}
	public Boolean getRefresh() {
		return refresh;
	}
	public void setRefresh(Boolean refresh) {
		this.refresh = refresh;
	}

	@Override
	public JsonObject toJson() {
		JsonObject json = super.toJson();
		if (getScript() != null) json.put(UpdateOptions.FIELD_SCRIPT, getScript());
    if (getScriptLang() != null) json.put(UpdateOptions.FIELD_SCRIPT_LANG, getScriptLang());
    if (getScriptParams() != null) json.put(UpdateOptions.FIELD_SCRIPT_PARAMS, getScriptParams());
    if (getScriptType() != null) json.put(UpdateOptions.FIELD_SCRIPT_TYPE, getScriptType().toString().toLowerCase());
    if(getAbortOnVersionConflict() != null) json.put(FIELD_ABORT_ON_VERSION_CONFLICT, getAbortOnVersionConflict());
    if (getConsistencyLevel() != null) {
      json.put(AbstractWriteOptions.FIELD_CONSISTENCY_LEVEL, getConsistencyLevel().toString().toLowerCase());
    }
    if(getRefresh() != null) json.put(AbstractOptions.FIELD_REFRESH, getRefresh());
		return json;
	}
	
}
