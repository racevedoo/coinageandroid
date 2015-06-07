package br.ufpe.cin.coinage.services;

import java.util.List;
import java.util.Map;

import br.ufpe.cin.coinage.android.MainApplication;
import br.ufpe.cin.coinage.database.DBHelper;
import br.ufpe.cin.coinage.model.Game;
import br.ufpe.cin.coinage.network.CoinageService;
import br.ufpe.cin.coinage.network.UpdatePriceCallback;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service{	

	private static final long ONE_MINUTE = 60000;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("[Service]", "service");
		doStuff();			

		//Para o service
		stopSelf();			
		//Caso o service morra, ele é reiniciado automaticamente.
		return START_STICKY;
	};

	@Override
	public void onDestroy() {
		//Setar o service para ser reiniciado após um minuto.
		AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
		alarm.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ONE_MINUTE, PendingIntent.getService(this, 0, new Intent(this, MyService.class), 0));
	};

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	private void doStuff() {
		Log.i("[Service]", "doStuff");
		
		CoinageService service = CoinageService.getInstance();
		
		Map<String, Integer> steamId = MainApplication.allSteamGames;		
		
		DBHelper db = MainApplication.getDBHelper();
		
		List<Game> games = db.getAllGames();
		
		UpdatePriceCallback callback = new UpdatePriceCallback(new Game("hue"), this);

		for (Game g : games) {			
			callback.setGame(g);
			
			System.out.println(g.getName());
			
			service.getSteamGamePrice(steamId.get(g.getName()), callback);
			service.getBuscapeGameByKeyword(g.getName(), callback);
		}	
		
	}






















}
