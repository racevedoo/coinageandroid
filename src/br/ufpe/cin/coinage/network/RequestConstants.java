package br.ufpe.cin.coinage.network;

public class RequestConstants {
	
	/*Buscape app token*/
	private static final String APP_TOKEN_BUSCAPE = "6f7074624f3134735179673d";
	
	/* APIs */
	private static final String URL_API_BUSCAPE = "http://sandbox.buscape.com/service/findProductList/" + APP_TOKEN_BUSCAPE + "/?";
	private static final String URL_APP_DETAILS_STEAM = "http://store.steampowered.com/api/appdetails/?";
	/* Services */
	
	/*Constant values*/
	
	private static final int GAMES_CATEGORY_BUSCAPE = 6409;
	private static final int PLATFORM_CATEGORY_BUSCAPE = 3712;
	private static final int PC_PLATFORM_CATEGORY_BUSCAPE = 349413;
	private static final int MAX_RESULTS_BUSCAPE = 5;
	
	/* Params */
	private static final String KEYWORD_QUERY_PARAM_BUSCAPE = "keyword";
	private static final String FORMAT_QUERY_PARAM_BUSCAPE = "format";
	private static final String CATEGORY_ID_QUERY_PARAM_BUSCAPE = "categoryId";
	private static final String SPEC_ID_QUERY_PARAM_BUSCAPE = "specId"+PLATFORM_CATEGORY_BUSCAPE;
	private static final String RESULTS_QUERY_PARAM_BUSCAPE = "results";
	private static final String APPIDS_QUERY_PARAM_STEAM = "appids";
	
	/*Callable URLs*/
	public static final String URL_ALL_GAMES_STEAM = "http://api.steampowered.com/ISteamApps/GetAppList/v0001/";
	/**
	 * Format with %s for the keyword(replace spaces with dash (-))
	 */
	public static final String CALLABLE_URL_API_BUSCAPE = URL_API_BUSCAPE
													+ FORMAT_QUERY_PARAM_BUSCAPE + "=json&"
													+ CATEGORY_ID_QUERY_PARAM_BUSCAPE  + "=" + GAMES_CATEGORY_BUSCAPE + "&"
													+ SPEC_ID_QUERY_PARAM_BUSCAPE + "=" + PC_PLATFORM_CATEGORY_BUSCAPE + "&"
													+ RESULTS_QUERY_PARAM_BUSCAPE + "=" + MAX_RESULTS_BUSCAPE + "&"
													+ KEYWORD_QUERY_PARAM_BUSCAPE + "=%s";
	public static final String CALLABLE_URL_APP_DETAILS_STEAM = URL_APP_DETAILS_STEAM + APPIDS_QUERY_PARAM_STEAM + "%s";

	
}
