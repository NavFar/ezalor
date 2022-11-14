package ir.farahmand.ezalor.models;

import java.io.Serializable;

public class Target implements Serializable {

	private static final long serialVersionUID = 1930734022682781487L;
	private String url;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
