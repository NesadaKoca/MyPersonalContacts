package nesada.c4q.nyc.mypersonalcontacts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import nesada.c4q.nyc.mypersonalcontacts.adapters.AdapterDrops;
import nesada.c4q.nyc.mypersonalcontacts.adapters.AddListener;
import nesada.c4q.nyc.mypersonalcontacts.adapters.Dividier;
import nesada.c4q.nyc.mypersonalcontacts.adapters.SimpleTouchCallBack;
import nesada.c4q.nyc.mypersonalcontacts.beans.Drop;
import nesada.c4q.nyc.mypersonalcontacts.widgets.ContactRecyclerView;

public class ActivityMain extends AppCompatActivity {

    Toolbar mToolbar;
    Button mBtnAdd;
    ContactRecyclerView mRecycler; // this is our RecyclerView 
    Realm mRealm;
    RealmResults<Drop> mResults;
    AdapterDrops mAdapter;
    View mEmptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// ========this check if this app has permission to make a phone call, if not make a request for permission==========
        if (getApplicationContext().checkCallingOrSelfPermission("android.permission.CALL_PHONE") != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                    10);
        }
// ==================================================================================================================


        mRealm = Realm.getDefaultInstance();
        //we create a query for our database Realm and we save all our items in RealmResult, this is pretty much same like an ArrayList
        mResults = mRealm.where(Drop.class).findAllAsync();

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mEmptyView = (View)findViewById(R.id.empty_drops);
        mBtnAdd = (Button)findViewById(R.id.btn_add);
        mRecycler = (ContactRecyclerView) findViewById(R.id.rv_drops);
        mRecycler.addItemDecoration(new Dividier(this, LinearLayoutManager.VERTICAL));
        mRecycler.hideIfEmpty(mToolbar); // if recyclerView has no items, we are going to call this method 'hideIfEmpty' (hide toolbar.xml)
        mRecycler.showIfEmpty(mEmptyView); // if recyclerView has no items, we are going to call this method 'showIfEmpty' (show empty_drops.xml)
        mAdapter = new AdapterDrops(this,mRealm,mResults,mAddListener);
        mRecycler.setAdapter(mAdapter);
        SimpleTouchCallBack callBack = new SimpleTouchCallBack(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callBack);
        helper.attachToRecyclerView(mRecycler);

        mBtnAdd.setOnClickListener(mBtnAddListener);
        setSupportActionBar(mToolbar); //this makes the toolbar to appear
        initBackgroundImage(); // here we just call the method to put the background image

    }

    //just call the method 'showDialogAdd()'
    private View.OnClickListener mBtnAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDialogAdd();
        }
    };

    //if any data change in database,call the method update in AdapterDrop.class, so we update our RecyclerView
    private RealmChangeListener mChangeListener = new RealmChangeListener() {
        @Override
        public void onChange() {
            mAdapter.update(mResults);
        }
    };

    // this method open the dialog if the button 'Add a Drop' is clicked
    public void showDialogAdd() {
        DialogAdd dialog = new DialogAdd();
        dialog.show(getSupportFragmentManager(), "Add");

    }

    // this method is for the FOOTER button
    private AddListener mAddListener = new AddListener() {
        @Override
        public void add() {
        showDialogAdd();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        //we are using this to update our database
        mResults.addChangeListener(mChangeListener);
    }


    @Override
    protected void onStop() {
        super.onStop();
        //we are using this to update our database
        mResults.removeChangeListener(mChangeListener);
    }

    //this is the background image inserted programmatically.
    private void initBackgroundImage(){
        ImageView background = (ImageView)findViewById(R.id.iv_background);
        Glide.with(this)
                .load(R.drawable.background8)
                .centerCrop()
                .into(background);
    }


}
