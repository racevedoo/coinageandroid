package br.ufpe.cin.coinage.fragments;

import static br.ufpe.cin.coinage.utils.Constants.NAME_FRAGMENT_KEY;
import static br.ufpe.cin.coinage.utils.Constants.PRODUCTS_FRAGMENT_KEY;

import java.text.DecimalFormat;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import br.ufpe.cin.coinage.android.MainApplication;
import br.ufpe.cin.coinage.android.R;
import br.ufpe.cin.coinage.database.DBHelper;
import br.ufpe.cin.coinage.model.Game;
import br.ufpe.cin.coinage.model.Product;
import br.ufpe.cin.coinage.model.Store;

public class ComparePriceFragment extends Fragment{
	
	private TextView mNameTextView;
	private TextView mSteamPriceTextView;
	private TextView mBuscapePriceTextView;
	private Button mAddAlertButton;
	private String name;
	private List<Product> resultGames;
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static ComparePriceFragment newInstance() {
		return new ComparePriceFragment();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_compare_price, container, false);
		mNameTextView = (TextView) rootView.findViewById(R.id.name_tv);
		mSteamPriceTextView = (TextView) rootView.findViewById(R.id.steam_price_tv);
		mBuscapePriceTextView = (TextView) rootView.findViewById(R.id.buscape_price_tv);
		mAddAlertButton = (Button) rootView.findViewById(R.id.add_alert_btn);
		setupButton();
		Bundle args = getArguments();
		name = args.getString(NAME_FRAGMENT_KEY);
		resultGames = args.getParcelableArrayList(PRODUCTS_FRAGMENT_KEY);
		DecimalFormat decimalFormat = new DecimalFormat("R$ #,##");
		decimalFormat.setMinimumFractionDigits(2);
		decimalFormat.setMaximumFractionDigits(2);
		
		mNameTextView.setText(name);
		for(Product product : resultGames){
			if(product.getStore() == Store.STEAM){
				mSteamPriceTextView.setText(decimalFormat.format(product.getPrice()));
			}else if(product.getStore() == Store.BUSCAPE){
				mBuscapePriceTextView.setText(decimalFormat.format(product.getPrice()));
			}
		}
		return rootView;
	}
	
	private void setupButton(){
		mAddAlertButton.setText(MainApplication.getDBHelper().hasGame(name)? "Remover alerta" : "Adicionar alerta");
		mAddAlertButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DBHelper db = MainApplication.getDBHelper();
				if(!db.hasGame(name)){//jogo nao está nos alertas
					db.insertGame(new Game(name, resultGames));
				}else{
					db.removeGame(name);
				}
				mAddAlertButton.setText(mAddAlertButton.getText().equals("Adicionar alerta")? "Remover alerta" : "Adicionar alerta");
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
}
