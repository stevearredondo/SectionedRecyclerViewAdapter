package com.example.starredo.sectionedrvexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

class MySectionAdapter extends SectionedRecyclerViewAdapter<String, String, MySectionAdapter.VH> {

    public static final int HEADER = SectionedRecyclerViewAdapter.HEADER;
    public static final int ITEM = SectionedRecyclerViewAdapter.ITEM;

    public MySectionAdapter() {
        super();
    }

    public MySectionAdapter(boolean allowEmptySections) {
        super(allowEmptySections);
    }

    @Override
    public VH onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder, parent, false);
        VH vh = new VH(view);
        if (viewType == HEADER) {
            vh.header.setVisibility(VISIBLE);
            vh.item.setVisibility(GONE);
        } else {
            vh.header.setVisibility(GONE);
            vh.item.setVisibility(VISIBLE);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        final int viewType = holder.getItemViewType();
        switch (viewType) {
            case HEADER:
                final String headerText = getSectionHeaderAt(position);
                holder.header.setText(headerText);
                break;
            case ITEM:
                final String itemText = getSectionItemAt(position);
                holder.item.setText(itemText);
                break;
        }
    }

    static class VH extends RecyclerView.ViewHolder {
        final TextView header;
        final TextView item;
        VH(final View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.header);
            item = (TextView) itemView.findViewById(R.id.item);
        }
    }
}
