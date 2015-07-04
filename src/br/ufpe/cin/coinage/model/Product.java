package br.ufpe.cin.coinage.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Comparable, Parcelable{
	
	Store store;
	double price;
	String link;
	
	public Product(Store store, double price, String link) {
		this.store = store;
		this.price = price;
		this.link = link;
	}
	
	public Product(int store, double price, String link) {
		this.store = Store.fromInt(store);
		this.price = price;
		this.link = link;
	}	
	
	public Product(Parcel source) {
		this.store = Store.fromInt(source.readInt());
		this.price = source.readDouble();
		this.link = source.readString();
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.store.ordinal());
		dest.writeDouble(this.price);
		dest.writeString(this.link);	
	}
	
	public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
		 
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }
 
        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
	
}
