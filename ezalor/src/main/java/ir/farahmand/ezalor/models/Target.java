package ir.farahmand.ezalor.models;

import java.io.Serializable;



public class Target implements Serializable {
	public enum Type {
		TYPE1,
		TYPE2,
		TYPE3
	}

	private static final long serialVersionUID = 1930734022682781487L;
	private String url;
	private Type type;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
}
