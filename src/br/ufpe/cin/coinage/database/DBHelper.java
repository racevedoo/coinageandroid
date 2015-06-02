package br.ufpe.cin.coinage.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.ufpe.cin.coinage.database.tables.GameProductTable;
import br.ufpe.cin.coinage.database.tables.GameTable;
import br.ufpe.cin.coinage.database.tables.ProductTable;
import br.ufpe.cin.coinage.model.Game;
import br.ufpe.cin.coinage.model.Product;

public class DBHelper extends SQLiteOpenHelper{	
	
	private SQLiteDatabase db;
	
	private static final String[] DATABASE_CREATE = { GameTable.CREATE_TABLE, ProductTable.CREATE_TABLE, GameProductTable.CREATE_TABLE };
	
	private static final String[] DATABASE_DELETE = { GameProductTable.DROP_TABLE, ProductTable.DROP_TABLE, GameTable.DROP_TABLE };
	
	public static final String DATABASE_NAME = "Coinage.db";
	public static final int DATABASE_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {		
		for (String sql : DATABASE_CREATE)
			db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub		
	}
	
	public void clean() {
		Log.i("[DB]", "CLEAN");
		db = this.getWritableDatabase();
		for (String sql : DATABASE_DELETE)
			db.execSQL(sql);
		
		for (String sql : DATABASE_CREATE)
			db.execSQL(sql);
	}
	
	public void insertGame(Game game) {	
		this.db = this.getWritableDatabase();
		
		long gameId = insertGameTable(game.getName(), game.isAlert());
		Log.i("[DB]", String.valueOf(gameId));
		
		if (gameId != -1) {
			List<Long> productId = insertProducts(game.getProducts());
			
			for (long l : productId)
				insertGameProductTable(gameId, l);
		}		
	}
	
	public void removeGame(String name) {
		this.db = this.getWritableDatabase();
		this.db.delete(GameTable.TABLE_NAME, GameTable.COLUMN_NAME + "=" + "'" + name + "'", null);
	}	
	
	public Game getGame(String name) {
		Game game = getGameTable(name);
		game.addProducts(getProducts(name));
		
		return game;
	}
	
	public List<Product> getProducts(String... name) {
		this.db = this.getReadableDatabase();
		String sql = "Select P.Store, P.Price, P.Link"
				+ " FROM Product P, Game G, Game_Product GP"
				+ " where G." +GameTable.COLUMN_ID+ " = GP." + GameProductTable.COLUMN_GAME
				+ " and GP." +GameProductTable.COLUMN_PRODUCT+ " = P." + ProductTable.COLUMN_ID;
		
		if(name.length > 0){
			sql += " and G." +GameTable.COLUMN_NAME + " = " + "'" + name[0] + "' COLLATE NOCASE";		
		}
		Cursor c = this.db.rawQuery(sql, null);
		
		List<Product> products = new ArrayList<Product>();
		
		try {
			if (c != null) 
				c.moveToFirst();
			
			while(!c.isAfterLast()) {
				products.add(new Product(c.getInt(c.getColumnIndexOrThrow(ProductTable.COLUMN_STORE)),
						((double) c.getInt(c.getColumnIndexOrThrow(ProductTable.COLUMN_PRICE)))/100,
						c.getString(c.getColumnIndexOrThrow(ProductTable.COLUMN_LINK))));
				
				c.moveToNext();
			}
			
			return products;
			
		} finally {
			c.close();
		}		
	}
	
	
	
	
	private Game getGameTable(String name) {
		this.db = this.getReadableDatabase();
		
		String where = GameTable.COLUMN_NAME + "=" + "'" + name + "'";
		
		Cursor c = this.db.query(true, GameTable.TABLE_NAME, GameTable.ALL_COLUMNS, where, null, null, null, null, null);
		
		try {
			if (c != null)
				c.moveToFirst();		
		
			return new Game(c.getString(c.getColumnIndexOrThrow(GameTable.COLUMN_NAME)),
							(c.getInt(c.getColumnIndexOrThrow(GameTable.COLUMN_ALERTA)) == 1));	
		} finally {
			c.close();
		}
		
	}	
	
	public List<Game> getAllGames() {
		
		this.db = this.getReadableDatabase();
		
		List<Game> games = new ArrayList<Game>();
		
		Cursor c = this.db.query(true, GameTable.TABLE_NAME, GameTable.ALL_COLUMNS, null, null, null, null, null, null);
		if (c != null) c.moveToFirst();
		String name = "";
		boolean alert = false;
		Game toAdd = null;
		while(!c.isAfterLast()) {
			name = c.getString(c.getColumnIndexOrThrow(GameTable.COLUMN_NAME));
			alert = c.getInt(c.getColumnIndexOrThrow(GameTable.COLUMN_ALERTA)) == 1;
			toAdd = new Game(name, getProducts(name), alert);
			games.add(toAdd);
			c.moveToNext();
		}
		
		c.close();
		return games;		
	}
	
	public List<Game> getGamesAlert(){
		this.db = this.getReadableDatabase();
		
		List<Game> games = new ArrayList<Game>();
		
		Cursor c = this.db.query(true, GameTable.TABLE_NAME, GameTable.ALL_COLUMNS, GameTable.COLUMN_ALERTA + " = 1", null, null, null, null, null);
		if (c != null) c.moveToFirst();
		String name = "";
		boolean alert = false;
		Game toAdd = null;
		while(!c.isAfterLast()) {
			name = c.getString(c.getColumnIndexOrThrow(GameTable.COLUMN_NAME));
			alert = c.getInt(c.getColumnIndexOrThrow(GameTable.COLUMN_ALERTA)) == 1;
			toAdd = new Game(name, getProducts(name), alert);
			games.add(toAdd);
			c.moveToNext();
		}
		
		c.close();
		return games;
	}
	
	public void updateGame(Game game) {
		
		ContentValues values = new ContentValues();
        values.put(GameTable.COLUMN_ALERTA, game.isAlert());
        
        this.db = this.getWritableDatabase();
        db.update(GameTable.TABLE_NAME, values, GameTable.COLUMN_NAME + "=" + "'" + game.getName() + "'", null);
	}
	
	
	private List<Long> insertProducts(List<Product> products) {
		ArrayList<Long> productId = new ArrayList<Long>();
		
		for (Product p : products)
			productId.add(insertProductTable(p.getStore().ordinal(), p.getPrice(), p.getLink()));
		
		return productId;
	}
	
	private long insertGameProductTable(long game, long product) {
		ContentValues values = new ContentValues();
		values.put(GameProductTable.COLUMN_GAME, game);
		values.put(GameProductTable.COLUMN_PRODUCT, product);
		
		return this.db.insert(GameProductTable.TABLE_NAME, null, values);
	}
	
	private long insertProductTable(int store, double price, String link) {
		ContentValues values = new ContentValues();
		values.put(ProductTable.COLUMN_STORE, store);
		values.put(ProductTable.COLUMN_PRICE, price*100);
		values.put(ProductTable.COLUMN_LINK, link);
		
		return this.db.insert(ProductTable.TABLE_NAME, null, values);
	}
	
	private long insertGameTable(String name, boolean alert) {
		ContentValues values = new ContentValues();
		values.put(GameTable.COLUMN_NAME, name);
		values.put(GameTable.COLUMN_ALERTA, alert ? 1 : 0);
		
		return this.db.insertWithOnConflict(GameTable.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
	}
	
	
	

}
















