package poc.test.com.drawerwithormlite.application;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import poc.test.com.drawerwithormlite.model.DaoMaster;
import poc.test.com.drawerwithormlite.model.DaoSession;
import poc.test.com.drawerwithormlite.netcom.Api;
import poc.test.com.drawerwithormlite.netcom.ApiConstants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ashishthakur on 21/2/18.
 */

public class PocApp extends Application {
    public static PocApp instance;
    private static Retrofit retrofit = null;
    DaoSession daoSession;
    Database db;
    Api api;


    public static PocApp getInstance() {
        if (instance == null)
            instance = new PocApp();

        return instance;
    }

    public static Retrofit getClient(String BASE_URL) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "test-db");
        db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        retrofit = getClient(ApiConstants.BASE_URL);
        api = retrofit.create(Api.class);


    }

    public Api getApi() {
        return api;
    }


    public DaoSession getDaoSession() {
        return daoSession;
    }

    public Database getDatabse() {
        return db;
    }

}
