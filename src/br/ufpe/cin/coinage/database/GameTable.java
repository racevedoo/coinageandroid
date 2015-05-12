package br.ufpe.cin.coinage.database;

import android.provider.BaseColumns;

public abstract class GameTable implements BaseColumns{
	
	public static final String TABLE_NAME = "GAME";
	public static final String COLUMN_NAME = "NAME";
	public static final String COLUMN_STEAMPRICE = "STEAM_PRICE";
	public static final String COLUMN_BUSCAPEPRICE = "BUSCAPE_PRICE";
	public static final String COLUMN_STEAMLINK = "STEAM_LINK";
	public static final String COLUMN_BUSCAPELINK = "BUSCAPE_LINK";
	public static final String COLUMN_ALERTA = "ALERTA";
	
	public static final String[] ALL_COLUMNS = { TABLE_NAME, COLUMN_NAME, COLUMN_STEAMPRICE, COLUMN_BUSCAPEPRICE, 
		COLUMN_STEAMLINK, COLUMN_BUSCAPELINK, COLUMN_ALERTA };
	
}
