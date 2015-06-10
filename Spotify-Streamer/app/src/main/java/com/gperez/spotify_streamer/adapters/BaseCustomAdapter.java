package com.gperez.spotify_streamer.adapters;

import android.app.Activity;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by gabriel on 6/9/2015.
 */
public abstract class BaseCustomAdapter<E> extends BaseAdapter {
    protected Activity activity;
    protected List<E> adapterListItems;

    public BaseCustomAdapter(Activity mActivity) {
        this.activity = mActivity;
    }

    public BaseCustomAdapter(Activity mActivity, List<E> adapterListItems) {
        this.activity = mActivity;
        this.adapterListItems = adapterListItems;
    }

    @Override
    public int getCount() {
        if (adapterListItems == null) {
            return 0;
        }

        return adapterListItems.size();
    }

    @Override
    public Object getItem(int position) {
        if (adapterListItems == null) {
            return 0;
        }

        return adapterListItems.get(position);
    }

    public Activity getActivity() {
        return activity;
    }

    public List<?> getAdapterListItems() {
        return adapterListItems;
    }
}
