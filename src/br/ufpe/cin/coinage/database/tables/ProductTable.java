package br.ufpe.cin.coinage.database.tables;

import android.provider.BaseColumns;

public class ProductTable implements BaseTable{
	
	public static final String TABLE_NAME = "PRODUCT";
	public static final String COLUMN_ID = "ID";
	public static final String COLUMN_STORE = "STORE";
	public static final String COLUMN_PRICE = "PRICE";
	public static final String COLUMN_LINK = "LINK";
	
	public static final String[] ALL_COLUMNS = { COLUMN_ID, COLUMN_STORE, COLUMN_PRICE, COLUMN_LINK };
	
	public static final String CREATE_TABLE = 
			"CREATE TABLE " + TABLE_NAME + " (" +
			COLUMN_ID + INT_PK_AUTO + COMMA +
			COLUMN_STORE + INT_TYPE + COMMA +
			COLUMN_PRICE + INT_TYPE + COMMA +
			COLUMN_LINK + TEXT_TYPE + ");";
	
	public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
	
}
