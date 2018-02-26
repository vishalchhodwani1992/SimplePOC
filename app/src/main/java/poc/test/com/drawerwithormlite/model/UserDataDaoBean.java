package poc.test.com.drawerwithormlite.model;

import com.j256.ormlite.field.DatabaseField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by ashishthakur on 21/2/18.
 */
@SuppressWarnings("serial")
@Entity
public class UserDataDaoBean implements Serializable{
    String name;
    String fatherName;
    String gender;
    String id;
    @Generated(hash = 188632510)
    public UserDataDaoBean(String name, String fatherName, String gender,
            String id) {
        this.name = name;
        this.fatherName = fatherName;
        this.gender = gender;
        this.id = id;
    }
    @Generated(hash = 1966688693)
    public UserDataDaoBean() {
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFatherName() {
        return this.fatherName;
    }
    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
