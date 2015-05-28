package br.ufpe.cin.coinage.database.tables;

public class GameProductTable implements BaseTable{
	
	public static final String TABLE_NAME = "GAME_PRODUCT";
	public static final String COLUMN_GAME = "GAME_ID";
	public static final String COLUMN_PRODUCT = "PRODUCT_ID";

	public static final String[] ALL_COLUMNS = { COLUMN_ID, COLUMN_GAME, COLUMN_PRODUCT };
	
	public static final String CREATE_TABLE = 
			"CREATE TABLE " +TABLE_NAME+ " (" +
			COLUMN_ID + INT_PK_AUTO + COMMA +
			COLUMN_GAME + INT_TYPE + COMMA +
			COLUMN_PRODUCT + INT_TYPE + ");";
	
	public static final String DROP_TABLE = "DROP TABLE IF EXISTS " +TABLE_NAME;
	
}
