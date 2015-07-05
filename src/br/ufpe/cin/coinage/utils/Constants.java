package br.ufpe.cin.coinage.utils;

import br.ufpe.cin.coinage.android.R;
import br.ufpe.cin.coinage.fragments.MyGamesFragment;
import br.ufpe.cin.coinage.fragments.PreferencesFragment;

public class Constants {
	
	public static final int[] OPTIONS_TITLES = new int[] {
		R.string.title_section_search, R.string.title_preferences,
	};
	public static final String[] FRAGMENTS = new String[] {
		MyGamesFragment.class.getName(), PreferencesFragment.class.getName(),
	};
	
	public static String APPLIST_STEAM_JSON_KEY = "applist";
	public static String APPS_STEAM_JSON_KEY = "apps";
	public static String APP_STEAM_JSON_KEY = "app";
	
	public static String KEYWORD_FRAGMENT_KEY = "KEYWORD";
	public static String NAME_FRAGMENT_KEY = "NAME";
	public static String PRODUCTS_FRAGMENT_KEY = "PRODUCTS";
}
