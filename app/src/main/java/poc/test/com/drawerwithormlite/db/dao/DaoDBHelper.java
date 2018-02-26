package poc.test.com.drawerwithormlite.db.dao;

import android.content.Context;

import java.util.ArrayList;

import poc.test.com.drawerwithormlite.application.PocApp;
import poc.test.com.drawerwithormlite.model.UserDataDaoBean;

/**
 * Created by ashishthakur on 21/2/18.
 */

public class DaoDBHelper  extends CRUDHelper{
   public static DaoDBHelper dbHelper;


    public static DaoDBHelper getInstance(Context context) {

        if (dbHelper == null) {
            dbHelper = new DaoDBHelper(context);
        }
        return dbHelper;
    }

    public DaoDBHelper(Context context) {
        super(context);
    }



}
