package br.ufpe.cin.coinage.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.ufpe.cin.coinage.model.Game;

public class DBHelper extends SQLiteOpenHelper{	
	
	private SQLiteDatabase db;
	
	private static final String TEXT_TYPE = " TEXT NOT NULL";
	private static final String INT_TYPE = " INTEGER NOT NULL";
	private static final String COMMA_SEP = ", ";
	
	private static final String DATABASE_CREATE = "CREATE TABLE " + GameTable.TABLE_NAME + " (" +
			GameTable.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
			GameTable.COLUMN_STEAMPRICE + INT_TYPE + COMMA_SEP +
			GameTable.COLUMN_BUSCAPEPRICE + INT_TYPE + COMMA_SEP +
			GameTable.COLUMN_STEAMLINK + TEXT_TYPE + COMMA_SEP +
			GameTable.COLUMN_BUSCAPELINK + TEXT_TYPE + COMMA_SEP +
			GameTable.COLUMN_ALERTA + INT_TYPE + COMMA_SEP +
			"PRIMARY KEY (" + GameTable.COLUMN_NAME + "));";
	
	private static final String DATABASE_DELETE = "DROP TABLE IF EXISTS " + GameTable.TABLE_NAME;
	
	public static final String DATABASE_NAME = "Coinage.db";
	public static final int DATABASE_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub		
	}
	
	public void insertGame(Game game) {	
		ContentValues values = new ContentValues();
		values.put(GameTable.COLUMN_NAME, game.getName());
		values.put(GameTable.COLUMN_STEAMPRICE, game.getSteamPrice() * 100);
		values.put(GameTable.COLUMN_BUSCAPEPRICE, game.getBuscapePrice() * 100);
		values.put(GameTable.COLUMN_STEAMLINK, game.getSteamLink());
		values.put(GameTable.COLUMN_BUSCAPELINK, game.getBuscapeLink());
		values.put(GameTable.COLUMN_ALERTA, game.isAlert());
		
		this.db = this.getWritableDatabase();
		this.db.insert(GameTable.TABLE_NAME, null, values);		
	}
	
	public void removeGame(String name) {
		this.db = this.getWritableDatabase();
		this.db.delete(GameTable.TABLE_NAME, GameTable.COLUMN_NAME + "=" + "'" + name + "'", null);
	}	
	
	public List<Game> getAllGames() {
		
		this.db = this.getReadableDatabase();
		
		List<Game> games = new ArrayList<Game>();
		
		Cursor c = this.db.query(true, GameTable.TABLE_NAME, GameTable.ALL_COLUMNS, null, null, null, null, null, null);
		if (c != null)
			c.moveToFirst();	
		
		while(!c.isAfterLast()) {			
			games.add(new Game(c.getString(c.getColumnIndexOrThrow(GameTable.COLUMN_NAME)),
					((double) c.getInt(c.getColumnIndexOrThrow(GameTable.COLUMN_STEAMPRICE)))/100,
					((double)c.getInt(c.getColumnIndexOrThrow(GameTable.COLUMN_BUSCAPEPRICE)))/100,
					c.getString(c.getColumnIndexOrThrow(GameTable.COLUMN_STEAMLINK)),
					c.getString(c.getColumnIndexOrThrow(GameTable.COLUMN_BUSCAPELINK)),
					c.getInt(c.getColumnIndexOrThrow(GameTable.COLUMN_ALERTA)) == 1));			
			
			c.moveToNext();
		}
		
		c.close();
		return games;		
	}
	
	public void updateGame(Game game) {
		
		ContentValues values = new ContentValues();
        values.put(GameTable.COLUMN_STEAMPRICE, game.getSteamPrice());
        values.put(GameTable.COLUMN_BUSCAPEPRICE, game.getBuscapePrice());
        values.put(GameTable.COLUMN_ALERTA, game.isAlert());
        
        this.db = this.getWritableDatabase();
        db.update(GameTable.TABLE_NAME, values, GameTable.COLUMN_NAME + "=" + "'" + game.getName() + "'", null);
	}

}
















