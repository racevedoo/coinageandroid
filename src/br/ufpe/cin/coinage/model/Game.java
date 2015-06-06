package br.ufpe.cin.coinage.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
	
	private String name;
	private List<Product> products;
	private boolean alert;
	
	public Game(String name) {
		this.name = name;
		this.alert = false;
		this.products = new ArrayList<Product>();
	}
	
	public Game(String name, boolean alert) {
		this.name = name;
		this.alert = alert;
		this.products = new ArrayList<Product>();
	}	
		
	public Game(String name, List<Product> products, boolean alert) {
		this.name = name;
		this.products = products;
		this.alert = alert;
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
	
	public List<Product> getProducts() {	
		Collections.sort(this.products);
		return this.products;
	}
	
	public void addProduct(Product product) {
		this.products.add(product);
	}
	
	public void addProducts(List<Product> products) {
		this.products.addAll(products);
	}
	
	public void removeProduct(Store store) {
		for (Product p : this.products) 
			if (p.getStore().equals(store))
				this.products.remove(p);
	}
	
	public void removeAllProducts() {
		this.products = new ArrayList<Product>();
	}
	
	@Override
	public String toString() {		
		String s = "";
		
		s = s + "Nome: " +this.name+"\n";
		
		for (Product p : this.products)
			s = s + "Loja: " + p.getStore() + ", Preço: " +p.getPrice()+ ", Link: " +p.getLink() +"\n";
		
		s = s + "Alerta: " +this.alert + "\n";		
		
		return s;
	}
	
	
}
