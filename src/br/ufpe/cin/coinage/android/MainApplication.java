package br.ufpe.cin.coinage.android;

import br.ufpe.cin.coinage.database.DBHelper;
import br.ufpe.cin.coinage.network.NetworkQueue;
import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {
	private static Context context;
	private static DBHelper dbHelper;
	public static Context getContext(){
		return context;
	}
	
	public static DBHelper getDBHelper(){
		return dbHelper;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		NetworkQueue.getInstance().init(this);
		context = getApplicationContext();
		dbHelper = new DBHelper(context);
	}
	
}