package br.ufpe.cin.coinage.network;

import br.ufpe.cin.coinage.android.MainApplication;
import br.ufpe.cin.coinage.database.DBHelper;
import br.ufpe.cin.coinage.model.Game;
import br.ufpe.cin.coinage.model.Product;

public class UpdatePriceCallback implements NetworkRequestCallback<Product> {
	
	private Game game;
	private DBHelper db;
	
	public UpdatePriceCallback(Game game) {
		this.game = game;
		db = MainApplication.getDBHelper();
	}
	
	@Override
	public void onRequestResponse(Product response) {
		Product product = game.getProducts().get(0);
		if (response.getPrice() != product.getPrice()) {
			if (response.getPrice() < product.getPrice()) {
				//notificar usuário.
			}
			
			product.setPrice(response.getPrice());
			db.updatePrice(product);
		}
	}

	@Override
	public void onRequestError(Exception error) {
		// TODO Auto-generated method stub		
	}

	

}
