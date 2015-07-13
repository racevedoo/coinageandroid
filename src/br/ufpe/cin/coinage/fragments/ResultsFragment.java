package br.ufpe.cin.coinage.fragments;


import static br.ufpe.cin.coinage.utils.Constants.KEYWORD_FRAGMENT_KEY;
import static br.ufpe.cin.coinage.utils.Constants.NAME_FRAGMENT_KEY;
import static br.ufpe.cin.coinage.utils.Constants.PRODUCTS_FRAGMENT_KEY;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.ufpe.cin.coinage.adapters.ResultsAdapter;
import br.ufpe.cin.coinage.android.MainApplication;
import br.ufpe.cin.coinage.android.R;
import br.ufpe.cin.coinage.model.Product;
import br.ufpe.cin.coinage.model.Store;
import br.ufpe.cin.coinage.model.ui.ResultAdapterItem;
import br.ufpe.cin.coinage.network.CoinageService;
import br.ufpe.cin.coinage.network.NetworkRequestCallback;
import br.ufpe.cin.coinage.utils.Util;

public class ResultsFragment extends Fragment {

	private static final int NUMBER_OF_STORES = Store.values().length - 1;
	private CoinageService service;
	private ListView gamesListView;
	private ProgressDialog loadingGames;
	private ProgressDialog loadingPrices;
	private List<ResultAdapterItem> games;
	private String keyword;
	private List<Product> resultGames;
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static ResultsFragment newInstance() {
		return new ResultsFragment();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search, container, false);
		service = CoinageService.getInstance();
		gamesListView = (ListView) rootView.findViewById(R.id.steam_games_lstv);
		games = new ArrayList<ResultAdapterItem>();
		resultGames = new ArrayList<Product>(NUMBER_OF_STORES);
		keyword = getArguments().getString(KEYWORD_FRAGMENT_KEY);
		setupListView();
		getSteamGames();
		return rootView;
	}
	
	private void setupListView(){
		gamesListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ResultAdapterItem gameClicked = (ResultAdapterItem) parent.getItemAtPosition(position);
				int appid = gameClicked.getAppId();
				final String name = gameClicked.getName();
				
				loadingPrices = Util.showProgress(getActivity(), R.string.loading_prices);
				service.getSteamGamePrice(appid, new NetworkRequestCallback<Product>() {
					
					@Override
					public void onRequestResponse(Product response) {
						if(resultGames.size() > 0 && resultGames.get(0).getStore() == Store.STEAM){
							resultGames.clear();
						}
						resultGames.add(response.getStore().ordinal() - 1, response);
						if(resultGames.size() == NUMBER_OF_STORES){
							Util.hideProgress(loadingPrices);
							navigateToComparePriceFragment(name);
						}
						Log.i("[request]", "Preco: " + response.getPrice() + " Nome: " + response.getLink());
					}
					
					@Override
					public void onRequestError(Exception error) {
						Log.i("[request]", "Error retrieving game price");
					}
				});
				
				service.getBuscapeGameByKeyword(name, new NetworkRequestCallback<Product>() {
					
					@Override
					public void onRequestResponse(Product response) {
						if(resultGames.size() > 0 && resultGames.get(0).getStore() == Store.BUSCAPE){
							resultGames.clear();
						}
						resultGames.add(response.getStore().ordinal()-1, response);
						Log.i("[request]", "Preco buscape: " + response.getPrice() + " Nome: " + response.getLink());
						if(resultGames.size() == NUMBER_OF_STORES){
							Util.hideProgress(loadingPrices);
							navigateToComparePriceFragment(name);
						}
					}
					
					@Override
					public void onRequestError(Exception error) {
						Util.hideProgress(loadingPrices);
						Util.showLongToast(getActivity(), "Game not available on buscape");
						Log.i("[request]", "Error retrieving buscape game price");
						Log.i("[buscape]",error.toString());
					}
				});				
			}
		});
		gamesListView.setAdapter(new ResultsAdapter(getActivity(), games));
	}
	
	private void getSteamGames(){
		loadingGames = Util.showProgress(getActivity(), R.string.loading_steam_games);
		updateList(Util.getSteamGamesByKeyword(keyword));
		Util.hideProgress(loadingGames);
	}
	
	private void updateList(Map<String, Integer> response){
		games.clear();
		int cnt=0;
		long gamesQnt = PreferenceManager.getDefaultSharedPreferences(MainApplication.getContext()).getLong(getString(R.string.search_result_preference_key), -1);
		for(Entry<String, Integer> game : response.entrySet()){
			games.add(new ResultAdapterItem(game.getKey(), game.getValue()));
			cnt++;
			if(cnt>=gamesQnt)break;
		}
		gamesListView.invalidateViews();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
	
	private void navigateToComparePriceFragment(String name) {
		final Fragment fragment = ComparePriceFragment.newInstance();
		
		final Bundle args = new Bundle();
		args.putString(NAME_FRAGMENT_KEY, name);
		args.putParcelableArrayList(PRODUCTS_FRAGMENT_KEY, (ArrayList<? extends Parcelable>) resultGames);
		
		fragment.setArguments(args);
				
		getActivity().getFragmentManager()
			.beginTransaction()
			.replace(R.id.container, fragment)
			.addToBackStack(null)
			.commit();
	}
	
}
