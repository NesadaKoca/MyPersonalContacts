package nesada.c4q.nyc.mypersonalcontacts.extras;

import android.view.View;

import java.util.List;

/**
 * Created by Nesada on 10/29/2016.
 */
// we created this class to keep only this two methods that are going to show and hide views anytime that we call them, instead of copying the loop for multiple times

public class Util {

    public static void showViews (List<View> views){
        for (View v : views){
            v.setVisibility(View.VISIBLE);
        }
    }

    public static void hideViews (List<View> views){
        for (View v : views){
            v.setVisibility(View.GONE);
        }
    }

}
