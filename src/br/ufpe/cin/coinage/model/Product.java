package br.ufpe.cin.coinage.model;

public class Product implements Comparable{
	
	Store store;
	double price;
	String link;
	
	public Product(Store store, double price, String link) {
		this.store = store;
		this.price = price;
		this.link = link;
	}
	
	public Product(int store, double price, String link) {
		this.store = Store.values()[store];
		this.price = price;
		this.link = link;
	}	
	
	public Store getStore() {
		return store;
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

	@Override
	public int compareTo(Object another) {
		Product other = (Product) another;
		if (this.price < other.price) {
			return -1;
		}
		else if (this.price > other.price) {
			return 1;
		}
		else if (this.store.ordinal() < other.store.ordinal()) {
			return -1;
		}
		
		return 0;
	}

}
