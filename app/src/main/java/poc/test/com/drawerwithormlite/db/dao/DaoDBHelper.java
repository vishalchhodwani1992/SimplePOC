package poc.test.com.drawerwithormlite.db.dao;

import android.content.Context;

import java.util.ArrayList;

import poc.test.com.drawerwithormlite.application.PocApp;
import poc.test.com.drawerwithormlite.model.UserDataDaoBean;



public class DaoDBHelper  {
   public static DaoDBHelper dbHelper;


    public static DaoDBHelper getInstance() {
        if (dbHelper == null) {
            dbHelper = new DaoDBHelper();
        }
        return dbHelper;
    }



    public synchronized void insertUserData(ArrayList<UserDataDaoBean> userDataDaoBeen){

        if(userDataDaoBeen!=null&&userDataDaoBeen.size()>0){
            PocApp.getInstance().getDaoSession().getUserDataDaoBeanDao().deleteAll();
            PocApp.getInstance().getDaoSession().getUserDataDaoBeanDao().insertOrReplaceInTx(userDataDaoBeen);

        }
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
