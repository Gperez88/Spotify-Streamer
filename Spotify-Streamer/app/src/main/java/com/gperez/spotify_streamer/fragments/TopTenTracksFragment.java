package com.gperez.spotify_streamer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.activities.TopTenTracksActivity;
import com.gperez.spotify_streamer.adapters.ArtistTopTenAdapter;
import com.gperez.spotify_streamer.models.TrackTopTenArtistWrapper;
import com.gperez.spotify_streamer.tasks.AsyncTaskParams;
import com.gperez.spotify_streamer.tasks.TopTenTracksAsyncTask;

public class TopTenTracksFragment extends BaseManagerListViewInstanceFragment<ArtistTopTenAdapter, TrackTopTenArtistWrapper> {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_ten_tracks, container, false);
    }

    @Override
    protected void initComponents() {
        String artistId = getArguments().getString(TopTenTracksActivity.ARG_ARTIST_ID);

        AsyncTaskParams mAsyncTaskParams =
                new AsyncTaskParams(getActivity(), TopTenTracksFragment.this, loadData, containerListView, false);

        new TopTenTracksAsyncTask(mAsyncTaskParams).execute(artistId);
    }

    @Override
    protected void restoreListViewInstanceState() {
        //passing the instance of the collection of artist who keep turning the screen.
        if (adapterListItemsInstance != null) {
            ArtistTopTenAdapter artistTopTenAdapter = new ArtistTopTenAdapter(getActivity(), adapterListItemsInstance);
            getListView().setAdapter(artistTopTenAdapter);
        }
    }
}
