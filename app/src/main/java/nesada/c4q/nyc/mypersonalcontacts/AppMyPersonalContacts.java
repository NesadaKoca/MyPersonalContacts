package nesada.c4q.nyc.mypersonalcontacts;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Nesada on 10/27/2016.
 */
// this class is just to config our database once our app start running, we add this in the manifests .name, so we don't need to config the database every time that we need to use it

public class AppMyPersonalContacts extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // config the Realm database
        RealmConfiguration configuration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(configuration);
    }
}
