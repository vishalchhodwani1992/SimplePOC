package poc.test.com.drawerwithormlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;

import poc.test.com.drawerwithormlite.model.UserData;

/**
 * Created by ashishthakur on 21/2/18.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {
    public static String DATABASE_NAME="db";
    public static int DATABASE_VERSION=1;
    SQLiteDatabase sqLiteDatabase;

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
      /*  try {
            sqLiteDatabase = getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        sqLiteDatabase=database;
        try {
            TableUtils.createTable(connectionSource, UserData.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource,UserData.class,false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        onCreate(database,connectionSource);

    }



    /*Insertion code*/

    public void insertUserData(ArrayList<UserData>userDatas){
        if(userDatas!=null&&userDatas.size()>0){
            clearDataUser();
            for (int i=0;i<userDatas.size();i++){
                getRuntimeExceptionDao(UserData.class).create(userDatas.get(i));
            }
        }
    }
    /*Clear Table*/
    public void clearDataUser(){
        try {
            TableUtils.clearTable(connectionSource,UserData.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*getRecordsUser*/
    public ArrayList<UserData>getList(){
        ArrayList<UserData>userDatas=null;
        try {
            userDatas=(ArrayList<UserData>)getRuntimeExceptionDao(UserData.class).queryBuilder().query();
            return userDatas;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userDatas;
    }

}
