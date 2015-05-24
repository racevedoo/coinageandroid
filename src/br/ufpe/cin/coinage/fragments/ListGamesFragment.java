package br.ufpe.cin.coinage.fragments;


import static br.ufpe.cin.coinage.utils.Util.hideProgress;
import static br.ufpe.cin.coinage.utils.Util.showLongToast;
import static br.ufpe.cin.coinage.utils.Util.showProgress;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import br.ufpe.cin.coinage.activities.GamesActivity;
import br.ufpe.cin.coinage.adapters.GamesListAdapter;
import br.ufpe.cin.coinage.android.MainApplication;
import br.ufpe.cin.coinage.android.R;
import br.ufpe.cin.coinage.database.DBHelper;
import br.ufpe.cin.coinage.model.Game;
import br.ufpe.cin.coinage.model.SteamGame;
import br.ufpe.cin.coinage.network.CoinageService;
import br.ufpe.cin.coinage.network.NetworkRequestCallback;
import br.ufpe.cin.coinage.utils.Util;

public class ListGamesFragment extends Fragment {

	private static final String TAG = "ListGamesFragment";
	
	private CoinageService service;
	private ListView gamesListView;
	private ProgressDialog loadingGames;
	private List<SteamGame> games;
	private String previousSearchText = "";
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static ListGamesFragment newInstance() {
		return new ListGamesFragment();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search, container, false);
		service = new CoinageService();
		gamesListView = (ListView) rootView.findViewById(R.id.steam_games_lstv);
		games = new ArrayList<SteamGame>();
		setupListView();
		doAllGamesLocal();
		setHasOptionsMenu(true);
		return rootView;
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
	private void doAllGamesLocal(){
		loadingGames = showProgress(getActivity(), R.string.loading_steam_games);
		try {
			String all_games = Util.getRawResourceAsString(getActivity(), R.raw.all_games);
			updateList(Util.parseSteamGames(new JSONObject(all_games)));
		} catch (IOException e) {
			
		} catch (JSONException e) {

		} finally{
			hideProgress(loadingGames);
		}
		
	}
	private void doAllGames(){
		loadingGames = showProgress(getActivity(), R.string.loading_steam_games);
		service.getAllSteamGames(new NetworkRequestCallback<List<SteamGame>>() {

			public void onRequestResponse(List<SteamGame> response) {
				hideProgress(loadingGames);
				updateList(response);
			}

			public void onRequestError(Exception error) {
				hideProgress(loadingGames);
				showLongToast(MainApplication.getContext(), error.toString());
			}
			
		});
	}
	
	private void updateList(List<SteamGame> response){
		games.clear();
		for(SteamGame game : response){
			games.add(game);
		}
		gamesListView.invalidateViews();
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		if (!((GamesActivity) getActivity()).isDrawerOpen()) {
			inflater.inflate(R.menu.search_menu, menu);
			Util.showLongToast(getActivity(), "createoptions");
			SearchManager searchManager = 
					(SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
			SearchView searchView = 
					(SearchView) menu.findItem(R.id.action_search).getActionView();
			searchView.setSearchableInfo(
					searchManager.getSearchableInfo(getActivity().getComponentName()));
			searchView.setOnQueryTextListener(new OnQueryTextListener() {
				@Override
				public boolean onQueryTextSubmit(final String query) {
					Util.showLongToast(getActivity(), "querysubmit");
					return true;
				}
				
				@Override
				public boolean onQueryTextChange(String newText) {
					Util.showLongToast(getActivity(), newText);
					GamesListAdapter adapter = (GamesListAdapter) gamesListView.getAdapter();
					adapter.unhideAll();
					if(!newText.isEmpty()){
						for(int i = 0;i<adapter.getListItems().size();++i){
							if(!containsIgnoreCase(adapter.getListItems().get(i).getName(), newText)){
								adapter.hide(i);
							}
						}
					}
					adapter.notifyDataSetChanged();
					previousSearchText = newText;
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
	
}
