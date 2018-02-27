package poc.test.com.drawerwithormlite.netcom;

import java.util.ArrayList;

import poc.test.com.drawerwithormlite.model.CountryList;
import retrofit2.Call;
import retrofit2.http.GET;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


public interface Api{


    @GET(ApiConstants.getListCountry)
    Call<ArrayList<CountryList>> getTopRatedMovies();


    @GET(ApiConstants.getListCountry)
    Call<Object> getList();
}
