package br.ufpe.cin.coinage.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
	
	private String name;
	private List<Product> products;
	
	public Game(String name) {
		this.name = name;
		this.products = new ArrayList<Product>();
	}
	
	
		
	public Game(String name, List<Product> products) {
		this.name = name;
		this.products = products;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Product getCheaperProduct() {
		Product cheaper = this.products.get(0);
		
		for (Product p : this.products)
			if (p.compareTo(cheaper) < 0)
				cheaper = p;
		
		return cheaper;				
	}
	
	public List<Product> getProducts() {	
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
		
		return s;
	}	
	
}
