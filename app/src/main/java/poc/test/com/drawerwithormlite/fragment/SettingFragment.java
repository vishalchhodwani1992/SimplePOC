package poc.test.com.drawerwithormlite.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import poc.test.com.drawerwithormlite.R;
import poc.test.com.drawerwithormlite.activity.DrawerActivity;


public class SettingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.setting_fragment,container,false);
        ((DrawerActivity)getActivity()).slider.setVisibility(View.VISIBLE);
        ((DrawerActivity)getActivity()).back.setVisibility(View.GONE);
        return view;


    }
}
