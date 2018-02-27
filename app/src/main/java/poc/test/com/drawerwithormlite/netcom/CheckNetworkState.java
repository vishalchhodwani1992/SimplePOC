package poc.test.com.drawerwithormlite.netcom;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*
* used to check network info...
* */
public class CheckNetworkState {
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
       .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) {
            return activeNetwork.isConnectedOrConnecting();
        }
        NetworkInfo wifiNetwork = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null) {
            return wifiNetwork.isConnectedOrConnecting();
        }
        NetworkInfo mobileNetwork = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null) {
            return mobileNetwork.isConnectedOrConnecting();
        }
        NetworkInfo otherNetwork = cm.getActiveNetworkInfo();
        if (otherNetwork != null) {
            return otherNetwork.isConnectedOrConnecting();
        }
        return false;
    }
}