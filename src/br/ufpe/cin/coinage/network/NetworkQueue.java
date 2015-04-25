package br.ufpe.cin.coinage.network;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class NetworkQueue {
	
	private static final String TAG = NetworkQueue.class.getName();
	private static NetworkQueue mInstance;
	private Context mApplicationContext;
	private RequestQueue mRequestQueue;
	
	// Prevent instantiation
	private NetworkQueue() {
		super();
	}
	
	// Singleton
	public static NetworkQueue getInstance() {
		if(mInstance == null) {
			mInstance = new NetworkQueue();
		}
		return mInstance;
	}
	
	public void init(Application application) {
		if(mApplicationContext == null) {
			mApplicationContext = application.getApplicationContext();
			mRequestQueue = Volley.newRequestQueue(mApplicationContext);
		}
	}
	
	public void cancelRequestsByTag(Object tag) {
		mRequestQueue.cancelAll(tag);
	}
	
	public void cancelAllRequests() {
		mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {

			public boolean apply(Request<?> request) {
				return true;
			}
			
		});
	}	
	
	public void doRequest(MyRequest request, 
			NetworkRequestCallback<JSONObject> networkRequestCallback) {
		StringRequest volleyRequest = buildStringRequest(request, networkRequestCallback);
		volleyRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		doRequest(volleyRequest);
	}
	
	public void doJSONRequest(MyRequest request, 
			NetworkRequestCallback<JSONObject> networkRequestCallback) {
		JsonObjectRequest volleyRequest = buildJSONRequest(request, networkRequestCallback); 
		doRequest(volleyRequest);
	}
	
	private StringRequest buildStringRequest(final MyRequest request,
			final NetworkRequestCallback<JSONObject> networkRequestCallback) {
		final Map<String, String> internalHeaders;
		if(request.getHeaders() == null || request.getHeaders().isEmpty()) {
			internalHeaders = Collections.emptyMap();
		} else {
			internalHeaders = request.getHeaders();
		}
		
		String finalUrl = request.getUrl();
		if(request.getQueryParams() != null && !request.getQueryParams().isEmpty()) {
			finalUrl = buildQuery(request.getUrl(), request.getQueryParams());
		}
		
		Response.Listener<String> listener = new Response.Listener<String>() {

			public void onResponse(String response) {
				JSONObject json;
				try {
					json = new JSONObject(response);
					notifyOnResponse(json, networkRequestCallback);
				} catch (JSONException e) {
					throw new RuntimeException(e);
				}
			}
			
		};
		Response.ErrorListener errorListener = new Response.ErrorListener() {

			public void onErrorResponse(VolleyError error) {
				notifyOnErrorResponse(error, networkRequestCallback);			
			}
			
		};
		
		StringRequest stringRequest = new StringRequest(request.getMethod(), finalUrl, listener, errorListener) {
			
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				return buildHeaders(internalHeaders);
			}
			
			@Override
			protected Map<String,String> getParams()
					throws AuthFailureError {
				return request.getFormDataParams();
			};
			
			@Override
			protected Response<String> parseNetworkResponse(
					NetworkResponse response) {
				String contentTypeHeader = response.headers.get("Content-Type");
				if (contentTypeHeader.contains("json") 
						&& !contentTypeHeader.contains("charset")) {
					try {
						String jsonString = new String(response.data, "UTF-8");
						return Response.success(jsonString,
								HttpHeaderParser.parseCacheHeaders(response));
					} catch (UnsupportedEncodingException e) {
						return Response.error(new ParseError(e));
					}
				} else {
					return super.parseNetworkResponse(response);
				}
			}
		};
		
		stringRequest.setTag(request.getTag());
		
		return stringRequest;
	}
	
	private JsonObjectRequest buildJSONRequest(final MyRequest request,
			final NetworkRequestCallback<JSONObject> networkRequestCallback) {
		final Map<String, String> internalHeaders;
		if(request.getHeaders() == null || request.getHeaders().isEmpty()) {
			internalHeaders = Collections.emptyMap();
		} else {
			internalHeaders = request.getHeaders();
		}
		
		String finalUrl = request.getUrl();
		if(request.getQueryParams() != null && !request.getQueryParams().isEmpty()) {
			finalUrl = buildQuery(request.getUrl(), request.getQueryParams());
		}
		
		Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {

			public void onResponse(JSONObject response) {
				notifyOnResponse(response, networkRequestCallback);				
			}
			
		};
		Response.ErrorListener errorListener = new Response.ErrorListener() {

			public void onErrorResponse(VolleyError error) {
				notifyOnErrorResponse(error, networkRequestCallback);
			}
			
		};
		
		JsonObjectRequest jrequest = new JsonObjectRequest(request.getMethod(), finalUrl, request.getJsonObject(), listener, errorListener) {
			
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				return buildHeaders(internalHeaders);
			}
			
			@Override
			protected Map<String,String> getParams()
					throws AuthFailureError {
				return request.getFormDataParams();
			};
		};
		
		jrequest.setTag(request.getTag());
		
		return jrequest;
	}
	
	private String buildQuery(String baseUrl, Map<String, String> queryParams) {
		StringBuilder urlBuilder = new StringBuilder();
		
		if(queryParams != null && !queryParams.isEmpty()) {
			urlBuilder.append(baseUrl);

			// Add params
			boolean isFirstParam = true;
			for (String paramName : queryParams.keySet()) {
				if(isFirstParam) {
					urlBuilder.append("?");
					isFirstParam = false;
				} else {
					urlBuilder.append("&");
				}
				urlBuilder.append(paramName);
				urlBuilder.append("=");
				urlBuilder.append(queryParams.get(paramName));
			}
		}
		
		return urlBuilder.toString();
	}
	
	private Map<String, String> buildHeaders(Map<String, String> baseHeaders) {
		HashMap<String, String> headers = new HashMap<String, String>(baseHeaders);
		//headers.put(CONTENT_TYPE_PARAM_KEY, CONTENT_TYPE_PARAM_VALUE);
		//headers.put(ACCEPT_PARAM_KEY, ACCEPT_PARAM_VALUE);
		return headers;
	}
	
	private <T> void doRequest(Request<T> request) {
		mRequestQueue.add(request);
	}
	
	private <T> void notifyOnResponse(T response,
			NetworkRequestCallback<T> networkRequestCallback) {
		Log.w(TAG, "Response:\n" + response.toString());
		if(networkRequestCallback != null) {
			networkRequestCallback.onRequestResponse(response);
		}
	}
	
	private <T> void notifyOnErrorResponse(VolleyError error,
			NetworkRequestCallback<T> networkRequestCallback) {
		if(error != null) {
			if (error.networkResponse != null) {
				Log.e(TAG, new String(error.networkResponse.data));
			}
			if (error.getMessage() != null && !error.getMessage().isEmpty()) {
				Log.e(TAG, error.getMessage());
			}
		}
		
		if(networkRequestCallback != null) {
			networkRequestCallback.onRequestError(error);
		}
	}
	
}
