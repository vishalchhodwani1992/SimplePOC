package poc.test.com.drawerwithormlite.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import poc.test.com.drawerwithormlite.R;
import poc.test.com.drawerwithormlite.Util.ProjectUtils;
import poc.test.com.drawerwithormlite.activity.DrawerActivity;
import poc.test.com.drawerwithormlite.adapter.ListAdapter;
import poc.test.com.drawerwithormlite.application.PocApp;
import poc.test.com.drawerwithormlite.db.dao.DaoDBHelper;
import poc.test.com.drawerwithormlite.model.CountryList;
import poc.test.com.drawerwithormlite.model.GetUserDetailsResponse;
import poc.test.com.drawerwithormlite.model.UserDataDaoBean;
import poc.test.com.drawerwithormlite.netcom.Controller;
import poc.test.com.drawerwithormlite.netcom.RequestType;
import poc.test.com.drawerwithormlite.netcom.TaskCompleteListner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements ListAdapter.OnItemClick {
    RecyclerView recyclerView;
    ArrayList<UserDataDaoBean> userDatas;
    ListAdapter listAdapter;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recylerView);
        userDatas = new ArrayList<>();
        userDatas.add(new UserDataDaoBean("arpit", "keshav", "male", "1"));
        userDatas.add(new UserDataDaoBean("aakshi", "krishan", "female", "2"));
        userDatas.add(new UserDataDaoBean("ashish", "mohan", "male", "3"));
        userDatas.add(new UserDataDaoBean("ankur", "ashish", "male", "4"));
        userDatas.add(new UserDataDaoBean("vishal", "sindhi", "male", "5"));
        userDatas.add(new UserDataDaoBean("jay", "kumar shukla", "male", "6"));
        ((DrawerActivity) getActivity()).slider.setVisibility(View.VISIBLE);
        ((DrawerActivity) getActivity()).back.setVisibility(View.GONE);

        /*Orm lIte Code*/
        //dbHelper=new DaoDBHelper(getActivity());
        //   dbHelper.insertUserData(userDatas);


                /*Green Dao Code*/
        DaoDBHelper.getInstance().insertUserData(userDatas);


/*
        PocApp.getInstance().getDaoSession().getUserDataDaoBeanDao().insertOrReplaceInTx(userDatas);
*/

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<UserDataDaoBean> userDatasList = new ArrayList<>();

        //  userDatasList=dbHelper.getList();
        userDatasList = DaoDBHelper.getInstance().getData();
        listAdapter = new ListAdapter(userDatasList, getActivity());
        recyclerView.setAdapter(listAdapter);
        listAdapter.setOnContactClick(this);
        getList();




        //m.invoke(d);// throws java.lang.IllegalAccessException


        return view;
    }

    @Override
    public void onClick(int id, int pos) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("data", userDatas.get(pos));
        DetailFragment detailFragment = new DetailFragment();
        ((DrawerActivity) getActivity()).replaceFragment(detailFragment, bundle, true);
    }


    public void getList() {
        showProgressDialoge();
        Call<ArrayList<CountryList>> countryListCall = PocApp.getInstance().getApi().getTopRatedMovies();
        countryListCall.enqueue(new Callback<ArrayList<CountryList>>() {
            @Override
            public void onResponse(Call<ArrayList<CountryList>> call, Response<ArrayList<CountryList>> response) {

                ProjectUtils.showLog("RESPONSE", "" + new Gson().toJson(response.body()), ProjectUtils.ERROR_LOG);

                hideProgreesDialoge();
            }

            @Override
            public void onFailure(Call<ArrayList<CountryList>> call, Throwable t) {
                t.printStackTrace();
                hideProgreesDialoge();

            }
        });
       /* HashMap<String, String> params = new HashMap<>();

        params.put("user_id", "2132");
        params.put("deviceType", "1");
        Controller.getInstance().doAction(getActivity(), RequestType.REQ_TYPE_TEST, params, GetUserDetailsResponse.class, true, new TaskCompleteListner() {
            @Override
            public void onTaskComplete(int methodName, CountryList obj) {
                Log.e("ResGson",""+new Gson().toJson(obj));


            }

            @Override
            public void onTaskCompleteError(int methodName, CountryList obj) {

            }
        });*/

    }





    public void showProgressDialoge() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            if(!progressDialog.isShowing()){
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("Please Wait..");
                progressDialog.show();
            }
        } else {

        }

    }

    public void hideProgreesDialoge() {
        if (progressDialog != null&&progressDialog.isShowing()) {
            progressDialog.dismiss();

        }


    }



}
