package com.gperez.spotify_streamer.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ListFragment;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.adapters.BaseCustomAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gabriel on 6/9/2015.
 */
public abstract class BaseManagerListViewInstanceFragment<T extends BaseCustomAdapter, E> extends ListFragment {
    protected static final String ADAPTER_KEY = "adapter_key";
    protected static final String LIST_VIEW_SELECTED_KEY = "list_view_selected_key";
    protected int mPosition = ListView.INVALID_POSITION;
    protected List<E> adapterListItemsInstance;

    protected LinearLayout containerListView;
    protected ProgressBar loadData;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (getListView().getAdapter() != null) {
            outState.putSerializable(ADAPTER_KEY, (Serializable)
                    ((T) getListView().getAdapter()).getAdapterListItems());

            outState.putParcelable(LIST_VIEW_SELECTED_KEY, getListView().onSaveInstanceState());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {

            if (savedInstanceState.containsKey(ADAPTER_KEY)) {
                adapterListItemsInstance = (List<E>)
                        savedInstanceState.getSerializable(ADAPTER_KEY);
            }

            if (savedInstanceState.containsKey(LIST_VIEW_SELECTED_KEY)) {
                mPosition = savedInstanceState.getInt(LIST_VIEW_SELECTED_KEY);
            }
        }

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
