package com.gperez.spotify_streamer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by gabriel on 6/10/2015.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initComponents();
    }

    protected abstract void initComponents();
}
