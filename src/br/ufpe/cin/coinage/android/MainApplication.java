package br.ufpe.cin.coinage.android;

import br.ufpe.cin.coinage.network.NetworkQueue;
import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {
	private static Context context;
	
	public static Context getContext(){
		return context;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		NetworkQueue.getInstance().init(this);
		context = getApplicationContext();
	}
	
}