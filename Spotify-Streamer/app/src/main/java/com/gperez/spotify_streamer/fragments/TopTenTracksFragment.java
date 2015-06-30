package com.gperez.spotify_streamer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.activities.TopTenTracksActivity;
import com.gperez.spotify_streamer.adapters.ArtistTopTenAdapter;
import com.gperez.spotify_streamer.models.ArtistWrapper;
import com.gperez.spotify_streamer.models.TrackTopTenArtistWrapper;
import com.gperez.spotify_streamer.tasks.AsyncTaskParams;
import com.gperez.spotify_streamer.tasks.TopTenTracksAsyncTask;

import java.util.List;

public class TopTenTracksFragment extends BaseManagerListViewInstanceFragment<ArtistTopTenAdapter, TrackTopTenArtistWrapper> {
    public interface Callback {

        void onItemSelected(List<TrackTopTenArtistWrapper> topTenTrackList, int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_ten_tracks, container, false);
    }

    @Override
    protected void initComponents() {
        ArtistWrapper artist = (ArtistWrapper) ((TopTenTracksActivity) getActivity()).getExtras().getSerializable(TopTenTracksActivity.ARG_ARTIST);

        AsyncTaskParams mAsyncTaskParams =
                new AsyncTaskParams(getActivity(), TopTenTracksFragment.this, loadData, containerListView, false);

        boolean mTowPane = ((TopTenTracksActivity) getActivity()).ismTwoPane();

        new TopTenTracksAsyncTask(mAsyncTaskParams, mTowPane).execute(artist);

    }

    @Override
    protected void restoreListViewInstanceState() {
        if (adapterListItemsInstance != null) {
            ArtistTopTenAdapter artistTopTenAdapter = new ArtistTopTenAdapter(getActivity(), adapterListItemsInstance);
            getListView().setAdapter(artistTopTenAdapter);

            if (mPosition != ListView.INVALID_POSITION) {
                getListView().smoothScrollToPosition(mPosition);
            }
        }

    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        List<TrackTopTenArtistWrapper> topTenTrackList =
                ((ArtistTopTenAdapter) listView.getAdapter()).getAdapterListItems();

        ((Callback) getActivity()).onItemSelected(topTenTrackList, position);

        mPosition = position;
    }
}
