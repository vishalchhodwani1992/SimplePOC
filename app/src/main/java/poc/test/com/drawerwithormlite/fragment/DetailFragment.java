package poc.test.com.drawerwithormlite.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import poc.test.com.drawerwithormlite.R;
import poc.test.com.drawerwithormlite.activity.DrawerActivity;
import poc.test.com.drawerwithormlite.model.UserData;
import poc.test.com.drawerwithormlite.model.UserDataDaoBean;



public class DetailFragment extends Fragment {
    TextView name,fatherName,gender;
    UserDataDaoBean userData;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_detail_user,container,false);
        name=(TextView)view.findViewById(R.id.name);
        fatherName=(TextView)view.findViewById(R.id.fathername);
        gender=(TextView)view.findViewById(R.id.gender);
        userData=(UserDataDaoBean)getArguments().getSerializable("data");
        if(userData!=null){
            name.setText(userData.getName());
            fatherName.setText(userData.getFatherName());
            gender.setText(userData.getGender());
        }

        ((DrawerActivity)getActivity()).slider.setVisibility(View.GONE);
        ((DrawerActivity)getActivity()).back.setVisibility(View.VISIBLE);
        ((DrawerActivity)getActivity()).back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (getActivity()).onBackPressed();
            }
        });



        return view;

    }
}
