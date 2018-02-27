package poc.test.com.drawerwithormlite.Util;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import poc.test.com.drawerwithormlite.R;


public class ProjectUtils {
    public static final int ERROR_LOG = 1;

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

    public void loadImageFromUrl(String path, Context context, ImageView imageView) {
        Glide.with(context).load(path).
                placeholder(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

}
