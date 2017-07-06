package shen.pula.istria.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by dell on 2017/1/18.
 */

public class DividerUserInformation extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    private Drawable mDivider;
    private int mOrientation;

    public DividerUserInformation(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);

        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
//        Log.v("recyclerview - itemdecoration", "onDraw()");
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }

    }

    public void drawVertical(Canvas c, RecyclerView parent) {

        final int left = 0;
        final int right = parent.getWidth() ;
        final int childCount = parent.getChildCount();
        View child;
        int top;
        int bottom;
        RecyclerView.LayoutParams params;
        for (int i = 1; i < childCount-1; i++) {
            switch (i)
            {
                case 1:
                    break;
                case 2:
                    child = parent.getChildAt(i);
                     params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    top = child.getBottom() + params.bottomMargin;
                    bottom = top + mDivider.getIntrinsicHeight();
                        mDivider.setBounds(left, top, right, bottom);
                        mDivider.draw(c);
                    break;
                case 3:
                    break;
                case 4:
                    child = parent.getChildAt(i);
                    params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    top = child.getBottom() + params.bottomMargin;
                    bottom = top + mDivider.getIntrinsicHeight();
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                    break;
                case 5:
                case 6:
            }

        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            switch (i)
            {
                case 1:
                    outRect.set(0, 0, 0,mDivider.getIntrinsicHeight());
                    break;
                case 2:
                    outRect.set(0, 0, 0,0);
                    break;
                case 3:
                    outRect.set(0, 0, 0,0);
                    break;
                case 4:
                    outRect.set(0, 0, 0,0);
                    break;
                case 5:
                    outRect.set(0, 0, 0,0);
                case 6:
                    outRect.set(0, 0, 0,0);
                    break;
                case 7:
                    outRect.set(0, mDivider.getIntrinsicHeight(), 0,0);
                    break;
                case 8 :
                    outRect.set(0, 0, 0,0);
                    break;
            }

        }
    }
}
