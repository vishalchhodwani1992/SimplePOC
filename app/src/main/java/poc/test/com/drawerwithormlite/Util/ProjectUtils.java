package poc.test.com.drawerwithormlite.Util;

import android.util.Log;

/**
 * Created by ashishthakur on 22/2/18.
 */

public class ProjectUtils {
    public static final int  ERROR_LOG=1;

    public static void showLog(String Tag, String value, int logPrintingSection) {
        try {
            if (logPrintingSection == ERROR_LOG) {
                Log.e(Tag, value);
            } else {
                Log.d(Tag, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
