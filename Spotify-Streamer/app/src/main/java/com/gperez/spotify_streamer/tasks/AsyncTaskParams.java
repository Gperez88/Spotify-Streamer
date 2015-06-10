package com.gperez.spotify_streamer.tasks;

import android.app.Activity;
import android.support.v4.app.ListFragment;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * Created by gabriel on 6/9/2015.
 */
public class AsyncTaskParams {
    private Activity activity;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private ListFragment listFragment;
    private boolean searchFragment;

    public AsyncTaskParams(Activity activity, ListFragment listFragment, ProgressBar progressBar, LinearLayout linearLayout,boolean searchFragment) {
        this.activity = activity;
        this.listFragment = listFragment;
        this.progressBar = progressBar;
        this.linearLayout = linearLayout;
        this.searchFragment = searchFragment;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public ListFragment getListFragment() {
        return listFragment;
    }

    public void setListFragment(ListFragment listFragment) {
        this.listFragment = listFragment;
    }

    public boolean isSearchFragment() {
        return searchFragment;
    }

    public void setSearchFragment(boolean searchFragment) {
        this.searchFragment = searchFragment;
    }
}
