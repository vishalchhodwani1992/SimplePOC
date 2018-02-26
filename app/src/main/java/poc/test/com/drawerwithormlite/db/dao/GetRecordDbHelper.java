package poc.test.com.drawerwithormlite.db.dao;

import android.content.Context;

import java.util.ArrayList;

import poc.test.com.drawerwithormlite.application.PocApp;
import poc.test.com.drawerwithormlite.model.UserDataDaoBean;

/**
 * Created by ashishthakur on 21/2/18.
 */

public class GetRecordDbHelper {
    Context mContext;

    public GetRecordDbHelper(Context context) {
        mContext = context;
    }

    public synchronized ArrayList<UserDataDaoBean> getData() {
        ArrayList<UserDataDaoBean> messageList = null;
        try {
            messageList = (ArrayList<UserDataDaoBean>) PocApp.getInstance().getDaoSession().
                    getUserDataDaoBeanDao().queryBuilder()
                    .list();
            //  Collections.reverse(messageList);
            return messageList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messageList;
    }



}
