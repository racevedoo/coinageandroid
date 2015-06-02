package br.ufpe.cin.coinage.network;

import static br.ufpe.cin.coinage.network.RequestConstants.*;
import static org.apache.commons.httpclient.util.URIUtil.encodeQuery;
import br.ufpe.cin.coinage.model.SteamGame;
import br.ufpe.cin.coinage.utils.Util;

import java.util.List;

import org.apache.commons.httpclient.URIException;
import org.json.JSONObject;

import com.android.volley.Request;


public class CoinageService {

	private static final String TAG = CoinageService.class.getName();
	
	private NetworkQueue mNetworkQueue;

	private static CoinageService mInstance = null;
	private CoinageService() {
		mNetworkQueue = NetworkQueue.getInstance();
	}
	
	public static CoinageService getInstance(){
		if(mInstance == null){
			mInstance = new CoinageService();
		}
		return mInstance;
	}
	
	public void getBuscapeGameByKeyword(String keyword){
		keyword = keyword.replaceAll(" ","-");
		try {
			keyword = encodeQuery(keyword);
		} catch (URIException e) {
			//nothing
		}
		String url = String.format(CALLABLE_URL_API_BUSCAPE, keyword);
		MyRequest.Builder builder = new MyRequest.Builder();
        builder.setTag(TAG)
        	.setUrl(url)
        	.setMethod(Request.Method.GET);
        //TODO: add callback
        
	}
	
	public void getAllSteamGames(final NetworkRequestCallback<List<SteamGame>> callback){
		String url = URL_ALL_GAMES_STEAM;
		MyRequest.Builder builder = new MyRequest.Builder();
        builder.setTag(TAG)
        	.setUrl(url)
        	.setMethod(Request.Method.GET);
        mNetworkQueue.doRequest(builder.build(), new NetworkRequestCallback<JSONObject>() {
			
			public void onRequestResponse(JSONObject response) {
				try {
					callback.onRequestResponse(Util.parseSteamGames(response));
				} catch (Exception e) {
					onRequestError(e);
				}
			}
			
			public void onRequestError(Exception error) {
				callback.onRequestError(error);
				
			}
		});
	}
	
	public void getGamePrice(final int appId, 
			final NetworkRequestCallback<Double> callback){
		String url = String.format(CALLABLE_URL_APP_DETAILS_STEAM, appId);
		MyRequest.Builder builder = new MyRequest.Builder();
        builder.setTag(TAG)
        	.setUrl(url)
        	.setMethod(Request.Method.GET);
        mNetworkQueue.doRequest(builder.build(), new NetworkRequestCallback<JSONObject>() {
			
			public void onRequestResponse(JSONObject response) {
				try {
					callback.onRequestResponse(Util.parseGamePrice(appId,response));
				} catch (Exception e) {
					onRequestError(e);
				}
			}
			
			public void onRequestError(Exception error) {
				callback.onRequestError(error);
				
			}
		});
	}
	
	/* SESSION SERVICE */
	/**
	 * Calls the connect function from OKAPI
	 * @param domain
	 * @param user
	 * @param password
	 * @param callback
	 */
//	public void connect(String domain, String user, String password,
//			final NetworkRequestCallback<Void> callback) {
//        String url = URL_API_V1_OK_MOBILE +
//        		SESSION_ROUTE_OK_MOBILE + 
//        		CONNECT_REQUEST_SESSION_OK_MOBILE;
//        
//        Map<String, String> formData = new HashMap<String, String>();
//        String encode = encodeCredentials(domain, user, password);
//        formData.put(CREDENTIAL_FORM_PARAM_OK_MOBILE, encode);
//        
//        MyRequest.Builder builder = new MyRequest.Builder();
//        builder.setTag(TAG)
//        	.setUrl(url)
//        	.setMethod(Request.Method.POST)
//        	.setFormDataParams(formData);
//        
//        mNetworkQueue.doRequest(builder.build(),
//        		new NetworkRequestCallback<JSONObject>() {
//					
//					@Override
//					public void onRequestResponse(JSONObject response) {
//						try {
//							sessionId = response.getString(SESSION_ID_HEADER_PARAM_OK_MOBILE);
//							callback.onRequestResponse(null);
//						} catch (JSONException e) {
//							onRequestError(e);
//						}
//					}
//					
//					@Override
//					public void onRequestError(Exception error) {
//						callback.onRequestError(error);
//					}
//				});
//	}

	
}
