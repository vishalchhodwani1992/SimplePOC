package poc.test.com.drawerwithormlite.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import poc.test.com.drawerwithormlite.R;


/*
*
* Splash Screen for the  application
*
* */
public class SplashActivity extends AppCompatActivity {
    Context mContext;

    public SplashActivity() throws PackageManager.NameNotFoundException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = SplashActivity.this;
        navigateScreen();
    }


    /*
    * navigates to booklist screen
    * */
    public void navigateScreen() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                        startActivity(new Intent(mContext, DrawerActivity.class));
                        finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
