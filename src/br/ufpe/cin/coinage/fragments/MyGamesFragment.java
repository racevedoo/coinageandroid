package br.ufpe.cin.coinage.fragments;


import static br.ufpe.cin.coinage.utils.Util.hideProgress;
import static br.ufpe.cin.coinage.utils.Util.showLongToast;
import static br.ufpe.cin.coinage.utils.Util.showProgress;
import static br.ufpe.cin.coinage.utils.Constants.*;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

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
import br.ufpe.cin.coinage.adapters.GamesListAdapter;
import br.ufpe.cin.coinage.android.MainApplication;
import br.ufpe.cin.coinage.android.R;
import br.ufpe.cin.coinage.database.DBHelper;
import br.ufpe.cin.coinage.model.Game;
import br.ufpe.cin.coinage.model.SteamIdMapper;
import br.ufpe.cin.coinage.network.CoinageService;
import br.ufpe.cin.coinage.network.NetworkRequestCallback;
import br.ufpe.cin.coinage.utils.Util;

public class MyGamesFragment extends Fragment {

	private static final String TAG = "ListGamesFragment";
	
	private CoinageService service;
	private ListView gamesListView;
	private ProgressDialog loadingGames;
	private List<Game> games;
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static MyGamesFragment newInstance() {
		return new MyGamesFragment();
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
		games = new ArrayList<Game>();
		setupListView();
		doMyGames();
		setHasOptionsMenu(true);
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
		gamesListView.setAdapter(new GamesListAdapter(getActivity(), games));
	}
	
	private void doMyGames(){
		updateList(getDbHelper().getGamesAlert());
	}
	
	private void updateList(List<Game> response){
		games.clear();
		for(Game game : response){
			games.add(game);
		}
		gamesListView.invalidateViews();
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		if (!((GamesActivity) getActivity()).isDrawerOpen()) {
			inflater.inflate(R.menu.search_menu, menu);
			SearchManager searchManager = 
					(SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
			SearchView searchView = 
					(SearchView) menu.findItem(R.id.action_search).getActionView();
			searchView.setSearchableInfo(
					searchManager.getSearchableInfo(getActivity().getComponentName()));
			searchView.setOnQueryTextListener(new OnQueryTextListener() {
				@Override
				public boolean onQueryTextSubmit(final String query) {
					navigateToViewFragment(query);
					return true;
				}
				
				@Override
				public boolean onQueryTextChange(String newText) {
					/*Util.showLongToast(getActivity(), newText);
					GamesListAdapter adapter = (GamesListAdapter) gamesListView.getAdapter();
					if(!newText.isEmpty()){
						for(int i = 0;i<adapter.getListItems().size();++i){
							if(!containsIgnoreCase(adapter.getListItems().get(i).getName(), newText)){
								adapter.hide(i);
							}
						}
					}
					adapter.notifyDataSetChanged();
					previousSearchText = newText;*/
					return true;
				}
			});
		}
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
	
	private void navigateToViewFragment(String keyword) {
		final Fragment fragment = ResultsFragment.newInstance();
		
		final Bundle args = new Bundle();
		args.putString(KEYWORD_FRAGMENT_KEY, keyword);
		
		fragment.setArguments(args);
				
		getActivity().getFragmentManager()
			.beginTransaction()
			.replace(R.id.container, fragment)
			.addToBackStack(null)
			.commit();
	}
	
}
