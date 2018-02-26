package poc.test.com.drawerwithormlite.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import poc.test.com.drawerwithormlite.fragment.DetailFragment;
import poc.test.com.drawerwithormlite.fragment.DrawerFragmnet;
import poc.test.com.drawerwithormlite.R;
import poc.test.com.drawerwithormlite.adapter.DrawerAdapter;
import poc.test.com.drawerwithormlite.fragment.HomeFragment;
import poc.test.com.drawerwithormlite.fragment.SettingFragment;

public class DrawerActivity extends AppCompatActivity implements DrawerAdapter.OnItemClick {
    public DrawerLayout drawerLayout;
    public DrawerFragmnet drawerFragmnet;
    View containerFrag;
    public ImageView slider;
    public TextView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        setNavigationDrawer();
        replaceFragment(new HomeFragment(),null,false);



    }


    public void setNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_drawer);
        drawerFragmnet = (DrawerFragmnet) getSupportFragmentManager().findFragmentById(R.id.drawerListFragmnet);
        drawerFragmnet.setUpDrawer();
        drawerFragmnet.getAdapter().setOnContactClick(this);
        slider = (ImageView) findViewById(R.id.slider);
        back=(TextView)findViewById(R.id.back);
        containerFrag = findViewById(R.id.drawerListFragmnet);

        slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideDrawer();
            }
        });
    }

    @Override
    public void onClick(int id, int pos) {
        drawerLayout.closeDrawer(containerFrag);
        if(pos==0){
            replaceFragment(new HomeFragment(),null,false);
        }
        else if(pos==1){
            replaceFragment(new SettingFragment(),null,false);

        }
    }

    public void slideDrawer() {
        if (!drawerLayout.isDrawerOpen(containerFrag))
            drawerLayout.openDrawer(containerFrag);
        else
            drawerLayout.closeDrawer(containerFrag);
    }

    public void replaceFragment(Fragment fragment,Bundle bundle,boolean addBackstack){
        FragmentTransaction fragmentTransaction=addBackstack?getSupportFragmentManager().beginTransaction().addToBackStack(null)
                :getSupportFragmentManager().beginTransaction();
        if (bundle != null)
            fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.conatiner,fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment=getSupportFragmentManager().findFragmentById(R.id.conatiner);
        if(fragment instanceof DetailFragment){
            getSupportFragmentManager().popBackStack();
        }
        else if(fragment instanceof HomeFragment|| fragment instanceof SettingFragment){
            logoutAlert(DrawerActivity.this);
        }

    }

    public static void logoutAlert(final Context mContext) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialogTheme);
        builder.setTitle("test")
                .setMessage("quit")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity) mContext).finish();
                    }

                })
                .setNegativeButton("no", null);

        AlertDialog alert = builder.create();
        alert.show();
        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(ContextCompat.getColor(mContext, R.color.buttonNormal1));
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFocused2));
    }

}
