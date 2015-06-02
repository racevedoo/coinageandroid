package br.ufpe.cin.coinage.android;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufpe.cin.coinage.database.DBHelper;
import br.ufpe.cin.coinage.model.SteamGame;
import br.ufpe.cin.coinage.network.NetworkQueue;
import br.ufpe.cin.coinage.utils.Util;
import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {
	private static Context context;
	private static DBHelper dbHelper;
	public static List<SteamGame> allSteamGames;
	
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
		try {
			allSteamGames = Util.parseSteamGames(new JSONObject(Util.getRawResourceAsString(MainApplication.getContext(), R.raw.all_games)));
		} catch (JSONException e) {
			allSteamGames = null;
		} catch (IOException e) {
			allSteamGames = null;
		}
	}
	
}