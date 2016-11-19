package nesada.c4q.nyc.mypersonalcontacts.adapters;

/**
 * Created by Nesada on 10/29/2016.
 */
// this class is to implement 'onSwipe' method that we are going to use instead of deleting items from the RecyclerView

public interface SwipeListener {
    void onSwipe( int posistion);
}
