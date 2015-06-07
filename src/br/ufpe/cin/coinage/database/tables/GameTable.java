package br.ufpe.cin.coinage.database.tables;

public class GameTable implements BaseTable{
		
	public static final String TABLE_NAME = "GAME";
	public static final String COLUMN_NAME = "NAME";
	
	public static final String[] ALL_COLUMNS = { COLUMN_ID, COLUMN_NAME };
	
	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
			COLUMN_ID + INT_PK_AUTO + COMMA +
			COLUMN_NAME + TEXT_TYPE_U + ");";
	
	public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
	
}
