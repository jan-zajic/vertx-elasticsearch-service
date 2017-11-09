package com.englishtown.vertx.elasticsearch;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Options for admin put mapping operations
 */
@DataObject
public class MappingOptions {

    private Boolean ignoreConflicts;
    private Boolean updateAllTypes;

    public static final String JSON_FIELD_IGNORE_CONFLICTS = "ignoreConflicts";
    public static final String JSON_FIELD_UPDATE_ALL_TYPES = "updateAllTypes";

    public MappingOptions() {
    }

    public MappingOptions(MappingOptions other) {
        ignoreConflicts = other.ignoreConflicts;
    }

    public MappingOptions(JsonObject json) {
        ignoreConflicts = json.getBoolean(JSON_FIELD_IGNORE_CONFLICTS);
        updateAllTypes = json.getBoolean(JSON_FIELD_UPDATE_ALL_TYPES);
    }

    public Boolean shouldIgnoreConflicts() {
        return ignoreConflicts;
    }

    public MappingOptions setIgnoreConflicts(Boolean ignoreConflicts) {
        this.ignoreConflicts = ignoreConflicts;
        return this;
    }

    public Boolean getUpdateAllTypes() {
			return updateAllTypes;
		}

		public void setUpdateAllTypes(Boolean updateAllTypes) {
			this.updateAllTypes = updateAllTypes;
		}

		public JsonObject toJson() {
        JsonObject json = new JsonObject();
        if (ignoreConflicts != null) json.put(JSON_FIELD_IGNORE_CONFLICTS, ignoreConflicts);
        if (updateAllTypes != null) json.put(JSON_FIELD_UPDATE_ALL_TYPES, updateAllTypes);
        return json;
    }

}
