package com.gperez.spotify_streamer.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.tasks.TopTenTracksAsyncTask;

public class TopTenTracksFragment extends ListFragment {
    public static final String ARG_ARTIST_NAME = "arg-artist-name";
    public static final String ARG_ARTIST_ID = "arg-artist-id";

    public static TopTenTracksFragment create(String artistId) {
        TopTenTracksFragment mTopTenTracksFragment = new TopTenTracksFragment();

        Bundle args = new Bundle();
        args.putString(ARG_ARTIST_ID, artistId);
        mTopTenTracksFragment.setArguments(args);

        return mTopTenTracksFragment;
    }

    public TopTenTracksFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_ten_tracks, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        String artistId = getArguments().getString(ARG_ARTIST_ID);
        String artistName = getArguments().getString(ARG_ARTIST_NAME);
        getActivity().setTitle(artistName);

        new TopTenTracksAsyncTask(getActivity(), this).execute(artistId);
    }
}
