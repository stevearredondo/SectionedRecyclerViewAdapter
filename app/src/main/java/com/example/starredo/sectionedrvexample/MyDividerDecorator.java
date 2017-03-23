package com.example.starredo.sectionedrvexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;


class MyDividerDecorator extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private final Rect mBounds = new Rect();
    private int marginLeft;

    public MyDividerDecorator(Context context) {
        mDivider = context.getDrawable(android.R.drawable.divider_horizontal_textfield);
        marginLeft = context.getResources().getDimensionPixelSize(R.dimen.keyline);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }
        drawVertical(c, parent);
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int left;
        final int right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int bottom = mBounds.bottom + Math.round(ViewCompat.getTranslationY(child));
            final int top = bottom - mDivider.getIntrinsicHeight();
            mDivider.setBounds(left + marginLeft, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        final RecyclerView.ViewHolder viewHolder = parent.getChildViewHolder(view);
        final int viewType = viewHolder.getItemViewType();
        if (viewType == MySectionAdapter.ITEM) {
            int adapterPosition = viewHolder.getAdapterPosition();
            if (parent.getAdapter().getItemViewType(adapterPosition + 1) == MySectionAdapter.ITEM) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.setEmpty();
            }
        } else {
            outRect.setEmpty();
        }
    }
}