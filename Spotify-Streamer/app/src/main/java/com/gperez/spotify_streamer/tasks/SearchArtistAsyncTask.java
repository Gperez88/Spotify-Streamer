package com.gperez.spotify_streamer.tasks;

import android.app.Activity;
import android.widget.ListView;
import android.widget.Toast;

import com.gperez.spotify_streamer.R;

import com.gperez.spotify_streamer.adapters.ArtistAdapter;

import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by gabriel on 5/30/2015.
 */
public class SearchArtistAsyncTask extends BaseSearchAsyncTask {
    private ArtistAdapter mArtistAdapter;
    private ListView mListView;

    public SearchArtistAsyncTask(Activity mActivity, ListView mListView) {
        super(mActivity, R.string.title_progress_dialog, R.string.message_progress_dialog);
        this.mListView = mListView;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        if (params == null || params.length == 0) {
            return null;
        }

        String query = (String) params[0];

        ArtistsPager artistsPager = mSpotifyService.searchArtists(query);

        return artistsPager;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);

        if (result == null) {
            return;
        }

        ArtistsPager artistsPager = (ArtistsPager) result;
        int total = artistsPager.artists.total;
        final boolean dataFound = total > 0 ? true : false;

        if (dataFound) {
            mArtistAdapter.swapList(artistsPager.artists.items);
            mArtistAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(mActivity, mActivity.getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initAdapterListView() {
        mArtistAdapter = new ArtistAdapter(mActivity);
        mListView.setAdapter(mArtistAdapter);
    }
}
