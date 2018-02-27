package poc.test.com.drawerwithormlite.social;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import poc.test.com.drawerwithormlite.activity.DrawerActivity;


public class FacebookLogin {

    private final String TAG = "FacebookLogin";

    Context context;
     CallbackManager callbackManager;
    FacebookResponseListener facebookResponseListener;
    List<String> permissionNeeds = Arrays.asList("public_profile", "email", "user_birthday");
    ProgressDialog progressDialog;

    public FacebookLogin(Context context, FacebookResponseListener facebookResponseListener)
    {
        this.context = context;
        callbackManager = CallbackManager.Factory.create();
        this.facebookResponseListener = facebookResponseListener;
    }

    /**
     * This method is responsible to Login with Facebook
     * */
    public void facebookLogin(LoginButton loginButton)
    {
        try
        {
            showProgressDialoge();
           // progressDialog = Utils.showDialog(context);
            callbackManager = CallbackManager.Factory.create();
            // Set permissions
            LoginManager.getInstance().logInWithReadPermissions((DrawerActivity)context, permissionNeeds);

            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {

                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(
                                        JSONObject jsonObject,
                                        GraphResponse response) {
                                    // Application code
                                    JSONObject data = response.getJSONObject();
                                    Log.e(TAG, "facebookResponse=="+data.toString());
                                    facebookResponseListener.facebookResponse(jsonObject);
                                    hideProgreesDialoge();
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,birthday,gender,cover,friends,picture.type(large),first_name,last_name,token_for_business");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

                @Override
                public void onCancel() {
                    hideProgreesDialoge();
                }

                @Override
                public void onError(FacebookException error) {
                    hideProgreesDialoge();
                }
            });
        }
        catch (Exception ex)
        {
            hideProgreesDialoge();
            ex.printStackTrace();
        }
    }


    public void logout()
    {
        try
        {
            LoginManager.getInstance().logOut();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public interface FacebookResponseListener
    {
        /**
         * This method is responsible to get the result from Facebook after successfully login
         * @param jsonObject profile information in JsonObject
         * */
        void facebookResponse(JSONObject jsonObject);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void showProgressDialoge() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            if(!progressDialog.isShowing()){
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("Please Wait..");
                progressDialog.show();
            }
        } else {

        }

    }

    public void hideProgreesDialoge() {
        if (progressDialog != null&&progressDialog.isShowing()) {
            progressDialog.dismiss();

        }


    }
}
