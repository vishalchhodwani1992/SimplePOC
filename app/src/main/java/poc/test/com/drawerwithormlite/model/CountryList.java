package poc.test.com.drawerwithormlite.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ashishthakur on 22/2/18.
 */

public class CountryList {
    String name;

    String capital;

    String region;

    String nativeName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }
}
