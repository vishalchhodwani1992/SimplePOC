package poc.test.com.drawerwithormlite.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import poc.test.com.drawerwithormlite.R;
import poc.test.com.drawerwithormlite.activity.DrawerActivity;
import poc.test.com.drawerwithormlite.adapter.DrawerAdapter;

/**
 * Created by ashishthakur on 20/2/18.
 */

public class DrawerFragmnet extends Fragment {

    RecyclerView recyclerView;
    ArrayList<String> list;
    DrawerAdapter drawerAdapter;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_right_drawer, container, false);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.drawer_recylerview);
        list = new ArrayList<>();
        list.add("home");
        list.add("setting");
        list.add("notification");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        drawerAdapter = new DrawerAdapter(list, getActivity());
        recyclerView.setAdapter(drawerAdapter);

        return rootview;
    }

 /*   public interface OnDrawerItemSelected{
        public void onClick(int id,int pos);
    }


    public void setOnDrawerItemSelected(OnDrawerItemSelected onDrawerItemSelected) {
        this.onDrawerItemSelected = onDrawerItemSelected;
    }

    @Override
    public void onClick(int id, int pos) {
        onDrawerItemSelected.onClick(id,pos);
    }*/

    public DrawerAdapter getAdapter(){
        return drawerAdapter;
    }

    public void setUpDrawer(){
        actionBarDrawerToggle=new ActionBarDrawerToggle(getActivity(),((DrawerActivity)getActivity()).drawerLayout,R.string.open,R.string.close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        ((DrawerActivity)getActivity()).drawerLayout.addDrawerListener(actionBarDrawerToggle);
        ((DrawerActivity)getActivity()).drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                actionBarDrawerToggle.syncState();

            }
        });

    }

}
