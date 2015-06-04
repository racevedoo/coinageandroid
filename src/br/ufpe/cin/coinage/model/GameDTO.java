package br.ufpe.cin.coinage.model;

public class GameDTO {
	private double price;
	private String link;
	
	public GameDTO(double price, String link){
		this.price = price;
		this.link = link;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	
}
