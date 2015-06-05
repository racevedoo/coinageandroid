package br.ufpe.cin.coinage.utils;

import static br.ufpe.cin.coinage.utils.Constants.APPLIST_STEAM_JSON_KEY;
import static br.ufpe.cin.coinage.utils.Constants.APPS_STEAM_JSON_KEY;
import static br.ufpe.cin.coinage.utils.Constants.APP_STEAM_JSON_KEY;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;
import br.ufpe.cin.coinage.android.MainApplication;
import br.ufpe.cin.coinage.android.R;
import br.ufpe.cin.coinage.model.GameDTO;
import br.ufpe.cin.coinage.model.SteamGame;

public class Util {
	/**
	 * 
	 * @param activity
	 * @param messageStringResId
	 * @return
	 */
	public static ProgressDialog showProgress(Activity activity, int messageStringResId) {
		return Util.showProgress(activity, activity.getString(messageStringResId));
	}
	
	/**
	 * 
	 * @param activity
	 * @param message
	 * @return
	 */
	public static ProgressDialog showProgress(Activity activity, String message) {
		return Util.showProgress(activity, activity.getString(R.string.loading_default_title), message);
	}
	
	/**
	 * 
	 * @param activity
	 * @param titleStringResId
	 * @param messageStringResId
	 * @return
	 */
	public static ProgressDialog showProgress(Activity activity, int titleStringResId, int messageStringResId) {
		return Util.showProgress(activity, activity.getString(titleStringResId), activity.getString(messageStringResId));
	}
	
	/**
	 * 
	 * @param activity
	 * @param title
	 * @param message
	 * @return
	 */
	public static ProgressDialog showProgress(Activity activity, String title, String message) {
		ProgressDialog progressBar = new ProgressDialog(activity);
		progressBar.setCancelable(true);
		progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		if(title != null && !title.isEmpty()) {
			progressBar.setTitle(title);
		}
		progressBar.setMessage(message);
		progressBar.setCanceledOnTouchOutside(false);
		
		if (!activity.isFinishing()) {
			progressBar.show();
		}
		
		return progressBar;
	}
	
	/**
	 * 
	 * @param progressDialog
	 */
	public static void hideProgress(ProgressDialog progressDialog) {
		if(progressDialog != null) {
			progressDialog.dismiss();
		}
	}
	
	/**
	 * 
	 * @param context
	 * @param stringResId
	 */
	public static void showShortToast(Context context, int stringResId) {
		Toast.makeText(context, context.getString(stringResId), Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShortToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 
	 * @param context
	 * @param stringResId
	 */
	public static void showLongToast(Context context, int stringResId) {
		Toast.makeText(context, context.getString(stringResId), Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLongToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
	
	public static String getRawResourceAsString(Context context, int resId) throws IOException{
		InputStream is = context.getResources().openRawResource(resId);
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];
		try {
		    Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		    int n;
		    while ((n = reader.read(buffer)) != -1) {
		        writer.write(buffer, 0, n);
		    }
		} finally {
		    is.close();
		}

		return writer.toString();
	}
	
	public static List<SteamGame> parseSteamGames(JSONObject response) throws JSONException{
		JSONArray appList = response.getJSONObject(APPLIST_STEAM_JSON_KEY).getJSONObject(APPS_STEAM_JSON_KEY).getJSONArray(APP_STEAM_JSON_KEY);
		List<SteamGame> games = new ArrayList<SteamGame>();
		for(int i = 0; i < appList.length();++i){
			games.add(new SteamGame(appList.getJSONObject(i).getInt("appid"), appList.getJSONObject(i).getString("name")));
		}
		return games;
	}
	
	public static GameDTO parseSteamGame(int appid, JSONObject response) throws JSONException{
		JSONObject data = response.getJSONObject(appid+"").getJSONObject("data");
		int price100 = data.getJSONObject("price_overview").getInt("final");
		return new GameDTO(((double)price100 / 100.0), "http://store.steampowered.com/app/" + appid + "/");
	}
	
	public static GameDTO parseBuscapeGame(JSONObject response) throws Exception{
		JSONObject product = (JSONObject) response.getJSONArray("product").get(0);
		product = product.getJSONObject("product");
		double price = product.getDouble("pricemin");
		String link = "";
		try{
			link = ((JSONObject)product.getJSONArray("links").get(0)).getString("url");
		}catch(JSONException e){
			//nao tem url
		}
		if(!link.startsWith("http://www.buscape.com.br")){
			throw new Exception();
		}
		return new GameDTO(price, link);
	}
	
	public static List<SteamGame> getSteamGamesByKeyword(String keyword){
		List<SteamGame> ret = new ArrayList<SteamGame>();
		for(SteamGame game : MainApplication.allSteamGames){
			if(game.getName().toUpperCase().startsWith(keyword.toUpperCase())){
				ret.add(game);
			}
		}
		return ret;
	}
}

