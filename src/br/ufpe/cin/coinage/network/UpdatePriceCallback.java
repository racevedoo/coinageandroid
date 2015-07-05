package br.ufpe.cin.coinage.network;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import br.ufpe.cin.coinage.android.MainApplication;
import br.ufpe.cin.coinage.database.DBHelper;
import br.ufpe.cin.coinage.model.Game;
import br.ufpe.cin.coinage.model.Product;

public class UpdatePriceCallback implements NetworkRequestCallback<Product> {
	
	private Game game;
	private DBHelper db;
	private Service service;
	
	public UpdatePriceCallback(Game game, Service service) {
		this.game = game;
		db = MainApplication.getDBHelper();
		this.service = service;		
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	@Override
	public void onRequestResponse(Product response) {	
		Product product = game.getCheaperProduct();
		
		if (response.getPrice() != product.getPrice()) {
			if (response.getPrice() < product.getPrice()) {
				notifyUser(response);
			}
			
			product.setPrice(response.getPrice());
			db.updatePrice(product);
		}
	}

	@Override
	public void onRequestError(Exception error) {
		// TODO Auto-generated method stub		
	}
	
	private void notifyUser(Product product) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(MainApplication.getContext());
		builder.setSmallIcon(br.ufpe.cin.coinage.android.R.drawable.ic_launcher);
		builder.setContentTitle(game.getName() + " está mais barato! R$" + product.getPrice());
		builder.setContentText("Clique para ir ao site");
		builder.setAutoCancel(true);
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		System.out.println(product.getLink());
        intent.setData(Uri.parse(product.getLink()));
		
		PendingIntent resultPendingIntent =
			    PendingIntent.getActivity(
			    MainApplication.getContext(),
			    0,
			    intent,
			    PendingIntent.FLAG_CANCEL_CURRENT
			);
		
		builder.setContentIntent(resultPendingIntent);
		
		NotificationManager mNotifyMgr = (NotificationManager) this.service.getSystemService(this.service.getApplicationContext().NOTIFICATION_SERVICE);
		mNotifyMgr.notify(1, builder.build());		
	}
	
	

	

}
