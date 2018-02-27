package poc.test.com.drawerwithormlite.netcom;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/*
*
* this class used to server post or get request...
* */
public class Controller<T> {
    public static Controller instance = null;
    Class<T> clazz;
    String messageId, companyId;
    HashMap<String, String> params;
    boolean isProgressDialogShow = true;
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private final Gson gson = new Gson();
    String userDeletedMessageFromServer = "";
    TaskCompleteListner taskCompleteListener;


    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    /*
    * show progress dialog when waiting for response from server....
    * */
    public void showProgressDialog() {
        try {
            if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /*
    * hide progress dialog when response get successful or error..
    * */
    public void hideProgressDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    RequestQueue requestQueue = null;

    /*
    * this method will make request and get response from server...
    * */
    //Method FOR POST
    private void call(StringBuilder url, final int requestType,final TaskCompleteListner taskCompleteListner) {



        GsonPostRequest gsonObjRequest = new GsonPostRequest<T>(Request.Method.POST, mContext, url.toString(), clazz, null, params, new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                hideProgressDialog();
                Log.d("respone",""+response);
                taskCompleteListner.onTaskComplete(requestType,response);

        }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                hideProgressDialog();
            }
        }, requestType);


        Volley.newRequestQueue(mContext).add(gsonObjRequest);
    }




    public void doAction(Context context, int requestType, HashMap<String, String> params, Class<T> c, boolean isDialogShow,TaskCompleteListner taskCompleteListner) {
        mContext = context;
        this.params = params;
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {

        }
        taskCompleteListener=taskCompleteListner;
        this.isProgressDialogShow = isDialogShow;
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Progress...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        if (CheckNetworkState.isOnline(mContext)) {
            if (isDialogShow) {
                showProgressDialog();
            }
            StringBuilder URL_STRING = new StringBuilder(ApiConstants.BASE_URL);
            clazz = c;
            if(requestType==RequestType.REQ_TYPE_TEST){
                URL_STRING.append(ApiConstants.getListCountry);
            }

        call(URL_STRING,requestType,taskCompleteListner);
        }
    }
}