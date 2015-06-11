package com.gperez.spotify_streamer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.activities.PlayerActivity;
import com.gperez.spotify_streamer.activities.TopTenTracksActivity;
import com.gperez.spotify_streamer.adapters.ArtistTopTenAdapter;
import com.gperez.spotify_streamer.models.ArtistWrapper;
import com.gperez.spotify_streamer.models.TrackTopTenArtistWrapper;
import com.gperez.spotify_streamer.tasks.AsyncTaskParams;
import com.gperez.spotify_streamer.tasks.TopTenTracksAsyncTask;

public class TopTenTracksFragment extends BaseManagerListViewInstanceFragment<ArtistTopTenAdapter, TrackTopTenArtistWrapper> {

    public static TopTenTracksFragment create(ArtistWrapper artist) {
        TopTenTracksFragment mTopTenTracksFragment = new TopTenTracksFragment();

        Bundle args = new Bundle();
        args.putSerializable(TopTenTracksActivity.ARG_ARTIST, artist);
        mTopTenTracksFragment.setArguments(args);

        return mTopTenTracksFragment;
    }

    public TopTenTracksFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_ten_tracks, container, false);
    }

    @Override
    protected void initComponents() {
        ArtistWrapper artist = (ArtistWrapper)getArguments().getSerializable(TopTenTracksActivity.ARG_ARTIST);

        AsyncTaskParams mAsyncTaskParams =
                new AsyncTaskParams(getActivity(), TopTenTracksFragment.this, loadData, containerListView, false);

        new TopTenTracksAsyncTask(mAsyncTaskParams).execute(artist);
    }

    @Override
    protected void restoreListViewInstanceState() {
        //passing the instance of the collection of artist who keep turning the screen.
        if (adapterListItemsInstance != null) {
            ArtistTopTenAdapter artistTopTenAdapter = new ArtistTopTenAdapter(getActivity(), adapterListItemsInstance);
            getListView().setAdapter(artistTopTenAdapter);

            // Restore previous state (including selected item index and scroll position)
            if (stateListViewInstance != null) {
                getListView().onRestoreInstanceState(stateListViewInstance);
            }
        }
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        TrackTopTenArtistWrapper trackTopTenArtistWrapper =
                (TrackTopTenArtistWrapper) listView.getAdapter().getItem(position);

        Intent playerIntent = new Intent(getActivity(), PlayerActivity.class);
        playerIntent.putExtra(PlayerActivity.ARG_TOP_TEN_TRACK, trackTopTenArtistWrapper);

        startActivity(playerIntent);
    }
}
