package poc.test.com.drawerwithormlite.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.kalamansee.R;
import com.kalamansee.netcom.CheckNetworkState;
import com.kalamansee.utils.ConstantLib;
import com.kalamansee.utils.ProjectUtil;
import com.kalamansee.view.activity.HomeActivity;
import com.kalamansee.view.activity.LoginActivity;
import com.kalamansee.view.fragment.MyProfileFragment;
import com.kalamansee.view.fragment.VendorListFragment;

import java.util.ArrayList;

/**
 * Created by ashishthakur on 05/09/17.
 */
public class LocationUtils implements LocationListener, GoogleApiClient.ConnectionCallbacks {

    private LocationManager locationManager;
    private GoogleApiClient googleApiClient;
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;
    private Context mContext;
    private Activity activity;
    private Fragment fragment;

    LocationListener locationListner = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                locationManager.removeUpdates(locationListner);
                LoginActivity.longitude = location.getLongitude();
                LoginActivity.latitude = location.getLatitude();
                afterGettingLatLong(LoginActivity.latitude, LoginActivity.longitude, mContext);
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
            System.out.println("Not Found");
        }
    };
    public LocationUtils(Context context,Activity activity) {
        this.mContext = context;
        this.activity=activity;
        checkPermission(context);
    }
    public LocationUtils(Context context,Fragment fragment) {
        this.mContext = context;
        this.fragment=fragment;
        checkPermission(context);
    }

    public void checkPermission(final Context mContext) {
        try {
             TedPermission.with(mContext)
                    .setPermissionListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            if (CheckNetworkState.isOnline(mContext)) {
                                turnGPSOn(mContext);
                            } else {
                                showToastMsg(mContext, mContext.getResources().getString(R.string.txt_network_error));
                            }
                        }

                        @Override
                        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                            LoginActivity.latitude = 0.0;
                            LoginActivity.longitude = 0.0;
                        }
                    })
                    .setGotoSettingButton(false)
                    .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                    .check();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void turnGPSOn(final Context context) {
        try {
            if (isLocationEnabled(context)) {
                if(activity instanceof LoginActivity)
                {((LoginActivity) context).showProgressDialog(context);
                }
                GPSTracker gpsTracker = new GPSTracker(context);
                afterGettingLatLong(gpsTracker.getLatitude(), gpsTracker.getLongitude(), context);
            } else {
                locationManager = (LocationManager) context
                        .getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        0, 0, locationListner);
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0, 0, locationListner);
                googleApiClient = new GoogleApiClient.Builder(context)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(ConnectionResult connectionResult) {
                                ProjectUtil.showLog("TAG" + "", "Location error " + connectionResult.getErrorCode(),ConstantLib.DEBUG_LOG);
                            }
                        }).build();
                googleApiClient.connect();
                LocationRequest locationRequest = LocationRequest.create();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setInterval(100);
                locationRequest.setFastestInterval(100);
                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                        .addLocationRequest(locationRequest);
                builder.setAlwaysShow(true);
                PendingResult<LocationSettingsResult> result =
                        LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
                result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                    @Override
                    public void onResult(LocationSettingsResult result) {
                        final Status status = result.getStatus();
                        switch (status.getStatusCode()) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                try {
                                    status.startResolutionForResult(
                                            (Activity) context, ConstantLib.REQUEST_LOCATION);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }

                                break;
                            case LocationSettingsStatusCodes.SUCCESS:
                                break;
                        }
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
            showToastMsg(context, context.getString(R.string.txt_network_error));
        }
    }

    public boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Throwable e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    private void showToastMsg(Context mContext, String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void getLocation() {
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // getting network status
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isNetworkEnabled) {
            if (locationManager != null) {
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        0,
                        0, locationListner);
            } else {
            }
        } else if (isGPSEnabled) {
            if (locationManager != null) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0,
                        0, locationListner);
            }
        }
    }

    public void afterGettingLatLong(Double lat, Double lng, Context context) {
        try {
            if (lat != null && lng != null) {
                LoginActivity.latitude = lat;
                LoginActivity.longitude = lng;
                ProjectUtil.showLog("Lat and Long is",""+lat+"  "+"  "+lng,ConstantLib.ERROR_LOG);
                if(activity instanceof LoginActivity){
                    ((LoginActivity) context).navigate();
                }
                else if(fragment instanceof VendorListFragment){
                    ((VendorListFragment) fragment).navigate();
                }
                else if(fragment instanceof MyProfileFragment){
                    ((MyProfileFragment) fragment).navigate();
                }
            } else {
                showToastMsg(context, context.getString(R.string.txt_network_error));
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

}
