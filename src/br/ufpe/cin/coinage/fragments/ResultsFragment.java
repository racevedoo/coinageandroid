package br.ufpe.cin.coinage.fragments;


import static br.ufpe.cin.coinage.utils.Constants.KEYWORD_FRAGMENT_KEY;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import br.ufpe.cin.coinage.activities.GamesActivity;
import br.ufpe.cin.coinage.adapters.ResultsAdapter;
import br.ufpe.cin.coinage.android.MainApplication;
import br.ufpe.cin.coinage.android.R;
import br.ufpe.cin.coinage.database.DBHelper;
import br.ufpe.cin.coinage.model.Game;
import br.ufpe.cin.coinage.model.SteamIdMapper;
import br.ufpe.cin.coinage.network.CoinageService;
import br.ufpe.cin.coinage.utils.Util;

public class ResultsFragment extends Fragment {

	private static final String TAG = "ListGamesFragment";
	
	private CoinageService service;
	private ListView gamesListView;
	private ProgressDialog loadingGames;
	private List<SteamIdMapper> games;
	private String keyword;
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
		games = new ArrayList<SteamIdMapper>();
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
				//nada
			}
		});
		gamesListView.setAdapter(new ResultsAdapter(getActivity(), games));
	}
	
	private void getSteamGames(){
		loadingGames = Util.showProgress(getActivity(), R.string.loading_steam_games);
		updateList(Util.getSteamGamesByKeyword(keyword));
		Util.hideProgress(loadingGames);
	}
	
	private void updateList(List<SteamIdMapper> response){
		games.clear();
		for(SteamIdMapper game : response){
			games.add(game);
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
