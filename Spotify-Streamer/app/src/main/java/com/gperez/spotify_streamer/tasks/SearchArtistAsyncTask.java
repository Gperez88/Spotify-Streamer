package com.gperez.spotify_streamer.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.Toast;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.adapters.ArtistAdapter;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by gabriel on 5/30/2015.
 */
public class SearchArtistAsyncTask extends AsyncTask<String, Void, ArtistsPager> {
    private Activity mActivity;
    private ListView mListView;

    private SpotifyApi mSpotifyApi;
    private SpotifyService mSpotifyService;
    private ArtistAdapter mArtistAdapter;
    private ProgressDialog mProgressDialog;

    public SearchArtistAsyncTask(Activity mActivity, ListView mListView) {
        this.mActivity = mActivity;
        this.mListView = mListView;
    }

    @Override
    protected void onPreExecute() {
        initSpotifyService();
        initProgressDialog();
        initAdapterListView();
    }

    @Override
    protected ArtistsPager doInBackground(String... params) {
        if (params == null || params.length == 0) {
            return null;
        }

        String query = params[0];

        ArtistsPager mArtistsPager = mSpotifyService.searchArtists(query);

        return mArtistsPager;
    }

    @Override
    protected void onPostExecute(ArtistsPager artistsPager) {
        mProgressDialog.dismiss();

        if (artistsPager == null) {
            return;
        }

        int total = artistsPager.artists.total;
        final boolean dataFound = total > 0 ? true : false;

        if (dataFound) {
            mArtistAdapter.swapList(artistsPager.artists.items);
            mArtistAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(mActivity, mActivity.getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
        }
    }

    private void initSpotifyService() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
        String prefAccessTokenKey = mActivity.getString(R.string.pref_access_token);
        String prefAccessToken = prefs.getString(prefAccessTokenKey, null);

        mSpotifyApi = new SpotifyApi();
        mSpotifyApi.setAccessToken(prefAccessToken);

        mSpotifyService = mSpotifyApi.getService();
    }

    private void initProgressDialog(){
        mProgressDialog = new ProgressDialog(mActivity);
        mProgressDialog.setTitle(mActivity.getString(R.string.title_progress_dialog));
        mProgressDialog.setMessage(mActivity.getString(R.string.message_progress_dialog));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
    }

    private void initAdapterListView(){
        mArtistAdapter = new ArtistAdapter(mActivity);
        mListView.setAdapter(mArtistAdapter);
    }
}
