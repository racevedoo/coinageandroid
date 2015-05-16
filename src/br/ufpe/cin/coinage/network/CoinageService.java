package br.ufpe.cin.coinage.network;

import static br.ufpe.cin.coinage.network.RequestConstants.*;
import br.ufpe.cin.coinage.utils.Util;
import br.ufpe.cin.model.SteamGame;

import java.util.List;

import org.json.JSONObject;

import com.android.volley.Request;


public class CoinageService {

	private static final String TAG = CoinageService.class.getName();
	
	private NetworkQueue mNetworkQueue;

	
	public CoinageService() {
		mNetworkQueue = NetworkQueue.getInstance();
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
		String url = Util.formatURL(URL_APP_DETAILS_STEAM, APPIDS_QUERY_PARAM_STEAM, appId);
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
