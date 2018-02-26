package poc.test.com.drawerwithormlite.db.dao;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;

import poc.test.com.drawerwithormlite.application.PocApp;
import poc.test.com.drawerwithormlite.model.UserDataDaoBean;

/**
 * Created by ashishthakur on 21/2/18.
 */

public class CRUDHelper extends GetRecordDbHelper {
    public CRUDHelper(Context context) {
        super(context);
    }

    public synchronized void insertUserData(ArrayList<UserDataDaoBean> userDataDaoBeen){

        if(userDataDaoBeen!=null&&userDataDaoBeen.size()>0){
            PocApp.getInstance().getDaoSession().getUserDataDaoBeanDao().deleteAll();
            PocApp.getInstance().getDaoSession().getUserDataDaoBeanDao().insertOrReplaceInTx(userDataDaoBeen);

        }
    }
}
