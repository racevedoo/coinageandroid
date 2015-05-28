package br.ufpe.cin.coinage.database.tables;

public interface BaseTable {
	
	
	public static final String TEXT_TYPE = " TEXT NOT NULL";
	public static final String TEXT_TYPE_U = " TEXT UNIQUE NOT NULL";
	public static final String INT_TYPE = " INTEGER NOT NULL";
	public static final String INT_PK = " INTEGER PRIMARY KEY";
	public static final String INT_PK_AUTO = " INTEGER PRIMARY KEY AUTOINCREMENT";
	public static final String COMMA = ", ";

	public static final String COLUMN_ID = "ID";
			
}
