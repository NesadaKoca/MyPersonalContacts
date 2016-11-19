package nesada.c4q.nyc.mypersonalcontacts.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import nesada.c4q.nyc.mypersonalcontacts.R;

/**
 * Created by Nesada on 10/29/2016.
 */
// this class we are going to use to divide items from each-other

public class Dividier extends RecyclerView.ItemDecoration {
    private Drawable mDividier;
    private int mOrientation;

    public Dividier (Context context, int orientation){
        mDividier = ContextCompat.getDrawable(context, R.drawable.dividier);
        if (orientation != LinearLayoutManager.VERTICAL){
            throw new IllegalArgumentException("This Item Decoration can be used only with a RecyclerView that uses a LinerLayoutMenager with vertical orientation!");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL){
            drawHorizontalDivider(c,parent,state);
        }
    }

    private void drawHorizontalDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left,top,right, bottom;

        left = parent.getPaddingLeft();
        right = parent.getWidth() - parent.getPaddingRight();
        int count = parent.getChildCount();
        for (int i = 0; i<count;i++){

            if (AdapterDrops.FOOTER != parent.getAdapter().getItemViewType(i)) {
                View current = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) current.getLayoutParams();
                top = current.getTop() - params.topMargin;
                bottom = top + mDividier.getIntrinsicHeight();
                mDividier.setBounds(left, top, right, bottom);
                mDividier.draw(c);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL){
            outRect.set(0,0,0, mDividier.getIntrinsicHeight());
        }


    }
}
