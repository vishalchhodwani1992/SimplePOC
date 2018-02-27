package poc.test.com.drawerwithormlite.netcom;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

/*
 *
* used volley for post requst..
* */
public class GsonPostRequest<T> extends Request<T> {
    String TAG = getClass().getSimpleName();
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;
    private final Map<String, String> params;
    int requestType;
    /**
     * Make home_message GET request and return home_message parsed object from JSON.
     * @param url
     * URL of the request to make
     * @param clazz
     * Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonPostRequest(int request, Context context, String url,
                           Class<T> clazz, Map<String, String> headers, Map<String, String> param,
                           Response.Listener<T> listener, Response.ErrorListener errorListener, int requestType) {
        super(request, url, errorListener);
        this.clazz = clazz;
        this.params = param;
        this.headers = headers;
        this.listener = listener;
        this.requestType = requestType;
        int MY_SOCKET_TIMEOUT_MS = 30000;
        this.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if(params!=null){
            Set<String> keys = params.keySet();  //get all keys
            for (String i : keys) {
            }
        }

    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            Log.e("resopnese",""+json);


            try {
//                Response<LogoutResponse> respons = Response.success(
//                        gson.fromJson(json, LogoutResponse.class),
//                        HttpHeaderParser.parseCacheHeaders(response));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (Exception e) {
            return Response.error(new VolleyError(" Unknown Exception"));
        }
    }
}