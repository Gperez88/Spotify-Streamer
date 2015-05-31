package com.gperez.spotify_streamer.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gperez.spotify_streamer.R;


public class TopTenTracksFragment extends Fragment {
    private ListView topTenTrackListView;

    public TopTenTracksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_ten_tracks, container, false);
    }
}
