package nesada.c4q.nyc.mypersonalcontacts.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;
import nesada.c4q.nyc.mypersonalcontacts.R;
import nesada.c4q.nyc.mypersonalcontacts.beans.Drop;

/**
 * Created by Nesada on 10/26/2016.
 */
// this class is like a manager for all drops

public class AdapterDrops extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeListener {
    public static final int ITEM = 0;
    public static final int FOOTER = 1;

    private Realm mRealm;
    private LayoutInflater mInflater;
    private RealmResults<Drop> mResults;
    private AddListener mAddListener;
    private Context mContext;

    //class constractor with 2 parameters
    public AdapterDrops(Context context,Realm realm, RealmResults<Drop> results){
        //this converts an xml file into a java object.(in this case, our row_drop.xml) This line is pretty much same thing when we use find.view.by.id for variables
        mInflater=LayoutInflater.from(context);
        mRealm = realm;
        update(results);
    }

    //class constractor 3 parameters
    public AdapterDrops(Context context, Realm realm, RealmResults<Drop> results, AddListener listener){
        //this converts an xml file into a java object.(in this case, our row_drop.xml) This line is pretty much same thing when we use find.view.by.id for variables
        mInflater=LayoutInflater.from(context);
        mRealm = realm;
        update(results);

        mAddListener = listener;
        mContext = context;
    }

    public void update (RealmResults<Drop> results){
        mResults = results;
        //this method tells our Adapter that something has changed
        notifyDataSetChanged();
    }

    // this method we are going to use just to add a footer in the RecyclerView
    @Override
    public int getItemViewType(int position) {
        //we return an item if results are null or if the position is within the bounds of the recyclerView
        if (mResults == null || position < mResults.size()){
            return ITEM;
        }else{
            return FOOTER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER){
            View view = mInflater.inflate(R.layout.footer, parent, false);
            return new FooterHolder(view, mAddListener);
        }else{
            View view = mInflater.inflate(R.layout.row_drop, parent, false);
            return new DropHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DropHolder){
            DropHolder dropHolder = (DropHolder) holder; // this line converts ViewHolder object into a DropHolder
            Drop drop = mResults.get(position);
            dropHolder.mWhat.setText(drop.getWhat());
            dropHolder.contactNumber.setText(drop.getPhone());
        }

    }

    @Override
    public int getItemCount() {
        if (mResults == null || mResults.isEmpty()){
            return 0;
        }else {
            return mResults.size() + 1;
        }
    }

    //this method we are using to delete items from Realm database
    @Override
    public void onSwipe(int posistion) {
        if (posistion < mResults.size()) { // we are using this if statement, because we don't want the FOOTER position
            mRealm.beginTransaction();
            mResults.get(posistion).removeFromRealm();
            mRealm.commitTransaction();
            notifyItemRemoved(posistion);
        }
    }

    //this class is gonna keep one drop in a current moment
    public class DropHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mWhat;
        TextView contactNumber;

    //class constractor
        public DropHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            mWhat = (TextView)itemView.findViewById(R.id.tv_what);
            contactNumber = (TextView) itemView.findViewById(R.id.tv_phone);
        }

//if user click on a contact (item) is going to start a phone call directly
        @Override
        public void onClick(View v) {
            if (mContext.getApplicationContext().checkCallingOrSelfPermission("android.permission.CALL_PHONE") == PackageManager.PERMISSION_GRANTED) {
                mContext.startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+contactNumber.getText().toString())));
            }}}

    //this class is pretty much the same like the class above
    public static class FooterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button mBtnAdd;
        AddListener mListener;
        //class constractor with 1 parameter
        public FooterHolder(View itemView) {
            super(itemView);
           mBtnAdd = (Button) itemView.findViewById(R.id.btn_footer);
            mBtnAdd.setOnClickListener(this); //this is for the FOOTER button
        }

        //class constractor with 2 parameters
        public FooterHolder(View itemView, AddListener listener) {
            super(itemView);
            mBtnAdd = (Button) itemView.findViewById(R.id.btn_footer);
            mBtnAdd.setOnClickListener(this); //this is for the FOOTER button
            mListener = listener;
        }
// this is for the FOOTER button
        @Override
        public void onClick(View v) {
            mListener.add();
        }
    }
}
