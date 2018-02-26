package poc.test.com.drawerwithormlite.netcom;

import java.util.ArrayList;

import poc.test.com.drawerwithormlite.model.CountryList;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ashishthakur on 22/2/18.
 */

public interface Api {

    @GET(ApiConstants.getListCountry)
    Call<ArrayList<CountryList>> getTopRatedMovies();
}
