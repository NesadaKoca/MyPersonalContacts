package nesada.c4q.nyc.mypersonalcontacts.beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Nesada on 10/23/2016.
 */

// we create this class to save all our variables in a single object, so we can use all the variables together like a single object for one item

public class Drop extends RealmObject {
    public Drop(String what, String phone) {

        this.what = what;
        this.phone = phone;
    }

    private String what;
    @PrimaryKey
    private String phone;

    public Drop() {
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
