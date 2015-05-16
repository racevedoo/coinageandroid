package br.ufpe.cin.coinage.network;

public class RequestConstants {
	
	/*Buscape app token*/
	public static final String APP_TOKEN_BUSCAPE = "6f7074624f3134735179673d";
	
	/* APIs */
	public static final String URL_API_BUSCAPE = "http://sandbox.buscape.com/service";
	public static final String URL_ALL_GAMES_STEAM = "http://api.steampowered.com/ISteamApps/GetAppList/v0001/";
	public static final String URL_APP_DETAILS_STEAM = "http://store.steampowered.com/api/appdetails";
	/* Services */
	public static final String FIND_PRODUCT_LIST_BUSCAPE = "/findProductList";
	
	
	/* Requests */
	public static final String CONNECT_REQUEST_SESSION_OK_MOBILE = "/connect";
	public static final String CLOSE_REQUEST_SESSION_OK_MOBILE = "/close";
	public static final String VALIDATE_REQUEST_SESSION_OK_MOBILE = "/validate";
	public static final String SEARCH_REQUEST_SEARCH_OK_MOBILE = "/contents";
	public static final String NEXT_REQUEST_SEARCH_OK_MOBILE = "/next";
	public static final String PREVIOUS_REQUEST_SEARCH_OK_MOBILE = "/previous";
	public static final String FACET_REQUEST_SEARCH_OK_MOBILE = "/facet";
	public static final String STATUS_REQUEST_OK_MOBILE = "/";
	public static final String GET_DOCUMENT_REQUEST_VIEW_OK_MOBILE = "/document";
	public static final String RESIZE_REQUEST_VIEW_OK_MOBILE = "/resize";
	public static final String AVAILABLE_REQUEST_LOCALE_OK_MOBILE = "/available";
	
	/* Params */
	public static final String KEYWORD_QUERY_PARAM_BUSCAPE = "keyword";
	public static final String FORMAT_QUERY_PARAM_BUSCAPE = "format";
	public static final String APPIDS_QUERY_PARAM_STEAM = "appids";
	
	public static final String NO_CONNECTION_ERROR_OK_MOBILE = "There isn't connection.";
	
}
