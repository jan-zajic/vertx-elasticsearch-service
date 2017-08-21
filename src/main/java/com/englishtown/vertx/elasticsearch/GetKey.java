package com.englishtown.vertx.elasticsearch;

public class GetKey {

	private String index;
	private String type;

	public GetKey() {
	}
	
	public GetKey(String index, String type) {
		super();
		this.index = index;
		this.type = type;
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
	
}
