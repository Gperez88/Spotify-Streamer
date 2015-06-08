package com.gperez.spotify_streamer.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.activities.TopTenTracksActivity;
import com.gperez.spotify_streamer.adapters.ArtistAdapter;
import com.gperez.spotify_streamer.adapters.ArtistTopTenAdapter;
import com.gperez.spotify_streamer.models.ArtistWrapper;
import com.gperez.spotify_streamer.models.TrackTopTenArtistWrapper;
import com.gperez.spotify_streamer.tasks.TopTenTracksAsyncTask;

import java.io.Serializable;
import java.util.List;

public class TopTenTracksFragment extends ListFragment {
    private static final String LIST_ADAPTER_INSTANCE_STATE = "top-ten-list-instance-state";
    private List<TrackTopTenArtistWrapper> artistWrapperListInstanceState;

    public static TopTenTracksFragment create(String artistId) {
        TopTenTracksFragment mTopTenTracksFragment = new TopTenTracksFragment();

        Bundle args = new Bundle();
        args.putString(TopTenTracksActivity.ARG_ARTIST_ID, artistId);
        mTopTenTracksFragment.setArguments(args);

        return mTopTenTracksFragment;
    }

    public TopTenTracksFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(LIST_ADAPTER_INSTANCE_STATE, (Serializable) ((ArtistTopTenAdapter) getListView().getAdapter()).getmTracksList());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            artistWrapperListInstanceState = (List<TrackTopTenArtistWrapper>) savedInstanceState.getSerializable(LIST_ADAPTER_INSTANCE_STATE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_ten_tracks, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        //passing the instance of the collection of artist who keep turning the screen.
        if (artistWrapperListInstanceState != null) {
            ArtistTopTenAdapter artistTopTenAdapter = new ArtistTopTenAdapter(getActivity(), artistWrapperListInstanceState);
            getListView().setAdapter(artistTopTenAdapter);
        } else {
            String artistId = getArguments().getString(TopTenTracksActivity.ARG_ARTIST_ID);
            new TopTenTracksAsyncTask(getActivity(), this).execute(artistId);
        }
    }
}
