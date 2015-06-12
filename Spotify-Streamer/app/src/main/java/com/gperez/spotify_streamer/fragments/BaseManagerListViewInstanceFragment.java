package com.gperez.spotify_streamer.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ListFragment;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.adapters.BaseCustomAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gabriel on 6/9/2015.
 */
public abstract class BaseManagerListViewInstanceFragment<T extends BaseCustomAdapter, E> extends ListFragment {
    protected static final String ADAPTER_INSTANCE_STATE_KEY = "adapter-instance-state";
    protected static final String LIST_VIEW_INSTANCE_STATE_KEY = "list-view-instance-state";
    protected List<E> adapterListItemsInstance;
    protected Parcelable stateListViewInstance;

    protected LinearLayout containerListView;
    protected ProgressBar loadData;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (getListView().getAdapter() != null) {

            outState.putSerializable(ADAPTER_INSTANCE_STATE_KEY, (Serializable)
                    ((T) getListView().getAdapter()).getAdapterListItems());

            outState.putParcelable(LIST_VIEW_INSTANCE_STATE_KEY, getListView().onSaveInstanceState());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            adapterListItemsInstance = (List<E>)
                    savedInstanceState.getSerializable(ADAPTER_INSTANCE_STATE_KEY);

            stateListViewInstance = savedInstanceState.getParcelable(LIST_VIEW_INSTANCE_STATE_KEY);
        }

    }

    @Override
    public void onPause() {
        // Save ListView state @ onPause
        stateListViewInstance = getListView().onSaveInstanceState();
        super.onPause();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        preInitComponents();
    }

    @Override
    public void onResume() {
        super.onResume();
        restoreListViewInstanceState();
    }

    private void preInitComponents() {
        containerListView = (LinearLayout) getView().findViewById(R.id.container_listview_linearlayout);
        loadData = (ProgressBar) getView().findViewById(R.id.load_progressbar);
        initComponents();
    }

    protected abstract void initComponents();

    protected abstract void restoreListViewInstanceState();
}
