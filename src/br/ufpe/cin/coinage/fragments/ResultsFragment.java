package br.ufpe.cin.coinage.fragments;


import static br.ufpe.cin.coinage.utils.Constants.KEYWORD_FRAGMENT_KEY;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
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
import br.ufpe.cin.coinage.database.DBHelper;
import br.ufpe.cin.coinage.model.Product;
import br.ufpe.cin.coinage.model.ui.ResultAdapterItem;
import br.ufpe.cin.coinage.network.CoinageService;
import br.ufpe.cin.coinage.network.NetworkRequestCallback;
import br.ufpe.cin.coinage.utils.Util;

public class ResultsFragment extends Fragment {

	private static final String TAG = "ListGamesFragment";
	
	private CoinageService service;
	private ListView gamesListView;
	private ProgressDialog loadingGames;
	private ProgressDialog loadingPrices;
	private List<ResultAdapterItem> games;
	private String keyword;
	private boolean steamFinished = false, buscapeFinished = false;
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
		keyword = getArguments().getString(KEYWORD_FRAGMENT_KEY);
		setupListView();
		getSteamGames();
		return rootView;
	}
	
	private DBHelper getDbHelper(){
		return MainApplication.getDBHelper();
	}
	
	private void setupListView(){
		gamesListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ResultAdapterItem gameClicked = (ResultAdapterItem) parent.getItemAtPosition(position);
				int appid = gameClicked.getAppId();
				String name = gameClicked.getName();
				
				loadingPrices = Util.showProgress(getActivity(), R.string.loading_prices);
				service.getSteamGamePrice(appid, new NetworkRequestCallback<Product>() {
					
					@Override
					public void onRequestResponse(Product response) {
						steamFinished = true;
						if(buscapeFinished)Util.hideProgress(loadingPrices);
						Util.showLongToast(getActivity(), "Preco: " + response.getPrice() + " Nome: " + response.getLink());
					}
					
					@Override
					public void onRequestError(Exception error) {
						steamFinished = true;
						if(buscapeFinished)Util.hideProgress(loadingPrices);
						Util.showLongToast(getActivity(), "Error retrieving game price");
					}
				});
				
				service.getBuscapeGameByKeyword(name, new NetworkRequestCallback<Product>() {
					
					@Override
					public void onRequestResponse(Product response) {
						buscapeFinished = true;
						if(steamFinished)Util.hideProgress(loadingPrices);
						Util.showLongToast(getActivity(), "Preco buscape: " + response.getPrice() + " Nome: " + response.getLink());
					}
					
					@Override
					public void onRequestError(Exception error) {
						buscapeFinished = true;
						if(steamFinished)Util.hideProgress(loadingPrices);
						Util.showLongToast(getActivity(), "Error retrieving game price");
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
		for(Entry<String, Integer> game : response.entrySet()){
			games.add(new ResultAdapterItem(game.getKey(), game.getValue()));
		}
		gamesListView.invalidateViews();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
	
	/*private void navigateToViewFragment(String keyword) {
		final Fragment fragment = ViewFragment.newInstance();
		
		final Bundle args = new Bundle();
		args.putString(KEYWORD_FRAGMENT_KEY, keyword);
		
		fragment.setArguments(args);
				
		getActivity().getFragmentManager()
			.beginTransaction()
			.replace(R.id.container, fragment)
			.addToBackStack(null)
			.commit();
	}*/
	
}
