package poc.test.com.drawerwithormlite.application;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.greendao.database.Database;

import poc.test.com.drawerwithormlite.model.DaoMaster;
import poc.test.com.drawerwithormlite.model.DaoSession;
import poc.test.com.drawerwithormlite.netcom.Api;
import poc.test.com.drawerwithormlite.netcom.ApiConstants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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


   /* private Retrofit getApiClient(String baseUrl) {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(new SelfSignInClient(mContext).getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }*/
}
