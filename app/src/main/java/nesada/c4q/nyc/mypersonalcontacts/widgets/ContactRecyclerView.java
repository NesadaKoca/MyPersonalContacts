package nesada.c4q.nyc.mypersonalcontacts.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nesada.c4q.nyc.mypersonalcontacts.extras.Util;

/**
 * Created by Nesada on 10/29/2016.
 */
// we use this class to check our recyclerView any time that something change in it( like item removed, or inserted, or moved, or changed)

public class ContactRecyclerView extends RecyclerView {

    private List<View> mNonEmptyViews = Collections.emptyList();
    private List<View> mEmptyViews = Collections.emptyList();

    private AdapterDataObserver mObserver = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            toggleViews();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            toggleViews();
        }

    };
// this method is gonna be called every time that something changed in the RecyclerView
    private void toggleViews() {
        //if the user has create an adapter, and we have some views to display then the condition is true
        if (getAdapter()!=null && !mEmptyViews.isEmpty() && !mNonEmptyViews.isEmpty()){
            //if recyclerView has no items
            if (getAdapter().getItemCount() == 0) {

                //show all the empty views from 'empty_drops.xml' using the method 'showViews' that we created in 'Util.class'
                Util.showViews(mEmptyViews);

                // hide the RecyclerView
                setVisibility(View.GONE);

                // hide all the items that are meant to be hidden from 'toolbar.xml' using the method 'hideViews' that we created in 'Util.class'
               Util.hideViews(mNonEmptyViews);
            }else{

                //hide all the empty views from 'empty_drops.xml'
                Util.hideViews(mEmptyViews);

                // show the RecyclerView
                setVisibility(View.VISIBLE);

                // show all the items that are meant to be hidden from 'toolbar.xml'
                Util.showViews(mNonEmptyViews);
            }

        }
    }

    //this constractor is used to initialize a recyclerView from code
    public ContactRecyclerView(Context context) {
        super(context);
    }
    //this constractor is used to initialize a recyclerView from xml
    public ContactRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    //this constractor is used to initialize a recyclerView from xml but in custom style
    public ContactRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    // this method we are using to notify our adapter mObserver if something changed
    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if (adapter != null){
            adapter.registerAdapterDataObserver(mObserver);
        }
        mObserver.onChanged();
    }
    // notice that the 3 dots before the parameter views means that this method can take as many views as parameters as we want, is like kind of an array of views
    public void hideIfEmpty(View ...views) {
        //all the views that we are going to have, we will put in this List
        mNonEmptyViews = Arrays.asList(views);
    }

    public void showIfEmpty(View ...emptyViews) {
        //all the views that we are going to have, we will put in this List
        mEmptyViews = Arrays.asList(emptyViews);
    }
}
