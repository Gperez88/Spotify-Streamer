package com.gperez.spotify_streamer.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.gperez.spotify_streamer.adapters.BaseCustomAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gabriel on 6/9/2015.
 */
public abstract class BaseManagerListViewInstanceFragment<T extends BaseCustomAdapter, E> extends ListFragment {
    protected static final String ADAPTER_INSTANCE_STATE_KEY = "adapter-instance-state";
    protected List<E> adapterListItemsInstance;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(ADAPTER_INSTANCE_STATE_KEY, (Serializable)
                ((T) getListView().getAdapter()).getAdapterListItems());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            adapterListItemsInstance = (List<E>)
                    savedInstanceState.getSerializable(ADAPTER_INSTANCE_STATE_KEY);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        postCreated();
    }

    @Override
    public void onResume() {
        super.onResume();
        restoreListViewInstanceState();
    }

    private void postCreated() {
        initComponents();
    }

    protected abstract void initComponents();
    protected abstract void restoreListViewInstanceState();
}
