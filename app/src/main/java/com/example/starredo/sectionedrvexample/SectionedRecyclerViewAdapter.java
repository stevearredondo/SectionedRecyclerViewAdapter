package com.example.starredo.sectionedrvexample;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * RecyclerView Adapter implementation to support lists divided into sections, each beginning
 * with a header item. The backing data type is a {@code LinkedHashMap<HEADER, List<ITEM>>} to maintain
 * the insertion order of the keys (sections). This class computes the item count as the sum of all
 * section items, plus their headers. By default, if a section is empty, then this adapter
 * <em>will not</em> display its header or count it toward the total item count.
 *
 * @param <HEADER>     Section header type corresponding to the view type {@link #HEADER}
 * @param <ITEM>       Section item type corresponding to the view type {@link #ITEM}
 * @param <VH>         ViewHolder type required by {@link android.support.v7.widget.RecyclerView.Adapter}
 */
public abstract class SectionedRecyclerViewAdapter<HEADER, ITEM, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private static final String TAG = SectionedRecyclerViewAdapter.class.getSimpleName();

    /** Section header view type */
    protected static final int HEADER = 0;

    /** Section item view type */
    protected static final int ITEM = 1;

    /** Map from section header object to section items. Using {@link java.util.LinkedHashMap} to enforce keySet order. */
    protected final Map<HEADER, List<ITEM>> sections = new LinkedHashMap<>();

    private int itemCount;

    private final boolean allowEmptySections;

    protected SectionedRecyclerViewAdapter() {
        this(false);
    }

    protected SectionedRecyclerViewAdapter(final boolean allowEmptySections) {
        this.allowEmptySections = allowEmptySections;
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    @Override
    public int getItemViewType(final int position) {
        int index = 0;
        for (HEADER header : sections.keySet()) {
            final List<?> items = sections.get(header);
            if (!items.isEmpty() || allowEmptySections) {
                if (position == index) {
                    return HEADER;
                }
                index += 1; // include header header
                if (position < index + items.size()) {
                    return ITEM;
                }
                index += items.size(); // include items in header
            }
        }
        Log.e(TAG, String.format("getItemViewType(%s) failed to return a valid type. Returning -1.", position));
        return -1;
    }

    public void update(@Nullable final LinkedHashMap<HEADER, List<ITEM>> data) {
        sections.clear();
        if (data != null) {
            sections.putAll(data);
        }
        updateItemCount();
    }

    protected void updateItemCount() {
        int count = 0;
        for (List<?> items : sections.values()) {
            // do not display section header if section is empty
            if (!items.isEmpty() || allowEmptySections) {
                count += items.size() + 1; // include items in section, plus the section header
            }
        }
        Log.e(TAG, "itemCount = " + itemCount);
        itemCount = count;
    }

    /**
     * To be used in {@link #onBindViewHolder(RecyclerView.ViewHolder, int)}.
     * @param position The position of the item within the adapter's data set
     * @return {@code HEADER} header data, or null if none found.
     */
    @Nullable
    protected HEADER getSectionHeaderAt(int position) {
        int index = 0;
        for (HEADER header : sections.keySet()) {
            final List<?> items = sections.get(header);
            if (!items.isEmpty() || allowEmptySections) {
                if (index == position) {
                    return header;
                }
                index += items.size() + 1; // include header items, plus the header header itself
            }
        }
        Log.e(TAG, String.format("No section header found in map corresponding to position %s.", position));
        return null;
    }

    /**
     * To be used in {@link #onBindViewHolder(RecyclerView.ViewHolder, int)}.
     * @param position The position of the item within the adapter's data set
     * @return {@code ITEM} data, or null if none found.
     */
    @Nullable
    protected ITEM getSectionItemAt(int position) {
        int index = 0;
        for (HEADER header : sections.keySet()) {
            final List<ITEM> items = sections.get(header);
            if (!items.isEmpty() || allowEmptySections) {
                index += 1; // include header header
                if (position < index + items.size()) {
                    return items.get(position - index);
                }
                index += items.size();
            }
        }
        Log.e(TAG, String.format("No section item found in map corresponding to position %s.", position));
        return null;
    }
}
