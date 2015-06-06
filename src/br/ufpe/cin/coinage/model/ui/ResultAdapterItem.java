package br.ufpe.cin.coinage.model.ui;

public class ResultAdapterItem {
	private String name;
	private int appId;
	
	public ResultAdapterItem(String name, int appId){
		this.name = name;
		this.appId = appId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}
	
}
