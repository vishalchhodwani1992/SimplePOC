package poc.test.com.drawerwithormlite.model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by ashishthakur on 21/2/18.
 */

public class UserData implements Serializable {
    @DatabaseField
    String name;
    @DatabaseField
    String fatherName;
    @DatabaseField
    String gender;
    @DatabaseField()
    String id;

public UserData(){}

    public UserData(String name, String fatherName, String gender, String id) {
        this.name = name;
        this.fatherName = fatherName;
        this.gender = gender;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }





}
