package br.ufpe.cin.coinage.model;

public class Game {
	
	private String name;
	private double steamPrice;
	private double buscapePrice;
	private String steamLink;
	private String buscapeLink;
	private boolean alert;
	
	public Game(String name, double steamPrice, double buscapePrice, String steamLink, String buscapeLink, boolean alert) {
		this.name = name;
		this.steamPrice = steamPrice;
		this.buscapePrice = buscapePrice;
		this.steamLink = steamLink;
		this.buscapeLink = buscapeLink;
		this.alert = alert;
	}
	
	public double getSteamPrice() {
		return steamPrice;
	}
	public void setSteamPrice(double steamPrice) {
		this.steamPrice = steamPrice;
	}
	public double getBuscapePrice() {
		return buscapePrice;
	}
	public void setBuscapePrice(double buscapePrice) {
		this.buscapePrice = buscapePrice;
	}
	public String getSteamLink() {
		return steamLink;
	}
	public void setSteamLink(String steamLink) {
		this.steamLink = steamLink;
	}
	public String getBuscapeLink() {
		return buscapeLink;
	}
	public void setBuscapeLink(String buscapeLink) {
		this.buscapeLink = buscapeLink;
	}	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isAlert() {
		return alert;
	}
	public void setAlert(boolean alert) {
		this.alert = alert;
	}
	
	@Override
	public String toString() {
		String s = "";
		
		s = this.name + ", " +this.steamPrice + ", " +this.buscapePrice + ", " +this.steamLink + ", " +this.buscapeLink + ", " +this.alert;
		
		return s;		
	}
}
