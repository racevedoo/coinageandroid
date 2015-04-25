package br.ufpe.cin.coinage.network;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class MyRequest {
	private String url;
	private Object tag;
	private JSONObject jsonObject;
	private Map<String, String> headers;
	private Map<String, String> queryParams;
	private Map<String, String> formDataParams;
	private int method;
	
	public static class Builder{
		private String url;
		private Object tag;
		private JSONObject jsonObject;
		private Map<String, String> headers;
		private Map<String, String> queryParams;
		private Map<String, String> formDataParams;
		private int method;
		
		public Builder(String url, Object tag, int method){
			this.url = url;
			this.tag = tag;
			this.method = method;
			this.jsonObject = null;
			this.headers = new HashMap<String, String>();
			this.formDataParams = new HashMap<String, String>();
		}
		public Builder(){
			this.url = "";
			this.tag = null;
			this.method = -1;
			this.jsonObject = null;
			this.formDataParams = new HashMap<String, String>();
		}

		public String getUrl() {
			return url;
		}

		public Builder setUrl(String url) {
			this.url = url;
			return this;
		}

		public Object getTag() {
			return tag;
		}

		public Builder setTag(Object tag) {
			this.tag = tag;
			return this;
		}

		public JSONObject getJsonObject() {
			return jsonObject;
		}

		public Builder setJsonObject(JSONObject jsonObject) {
			this.jsonObject = jsonObject;
			return this;
		}

		public Map<String, String> getHeaders() {
			return headers;
		}

		public Builder setHeaders(Map<String, String> headers) {
			this.headers = headers;
			return this;
		}
		
		public Map<String, String> getQueryParams() {
			return queryParams;
		}

		public Builder setQueryParams(Map<String, String> queryParams) {
			this.queryParams = queryParams;
			return this;
		}
		
		public Map<String, String> getFormDataParams() {
			return formDataParams;
		}

		public Builder setFormDataParams(Map<String, String> formDataParams) {
			this.formDataParams = formDataParams;
			return this;
		}

		public int getMethod() {
			return method;
		}
		
		public Builder setMethod(int method) {
			this.method = method;
			return this;
		}
		
		public Builder addHeader(String key, String value){
			this.headers.put(key, value);
			return this;
		}
		
		public Builder addQueryParams(String key, String value){
			this.queryParams.put(key, value);
			return this;
		}
		
		public MyRequest build(){
			return new MyRequest(this);
		}
	}
	
	private MyRequest(Builder builder){
		this.queryParams = builder.getQueryParams();
		this.headers = builder.getHeaders();
		this.formDataParams = builder.getFormDataParams();
		this.jsonObject = builder.getJsonObject();
		this.tag = builder.getTag();
		this.url = builder.getUrl();
		this.method = builder.getMethod();
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Object getTag() {
		return tag;
	}
	public void setTag(Object tag) {
		this.tag = tag;
	}
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	public Map<String, String> getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(Map<String, String> queryParams) {
		this.queryParams = queryParams;
	}
	public Map<String, String> getFormDataParams() {
		return formDataParams;
	}
	public void setFormDataParams(Map<String, String> formDataParams) {
		this.formDataParams = formDataParams;
	}
	public int getMethod() {
		return method;
	}
	public void setMethod(int method) {
		this.method = method;
	}
	
}