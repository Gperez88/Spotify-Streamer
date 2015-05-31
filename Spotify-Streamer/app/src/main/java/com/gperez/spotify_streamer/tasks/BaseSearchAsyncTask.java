package com.gperez.spotify_streamer.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.gperez.spotify_streamer.services.SpotifyApi;

import kaaes.spotify.webapi.android.SpotifyService;

/**
 * Created by gabriel on 5/30/2015.
 */
public abstract class BaseSearchAsyncTask extends AsyncTask {
    protected Activity mActivity;
    protected ProgressDialog mProgressDialog;
    protected SpotifyService mSpotifyService;

    public BaseSearchAsyncTask(Activity mActivity, int titleProgressBarRes, int messageProgressBarRes) {
        this.mActivity = mActivity;
        this.mSpotifyService = SpotifyApi.getInstance(mActivity).getService();

        initProgressDialog(titleProgressBarRes, messageProgressBarRes);
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog.show();
        initAdapterListView();
    }

    @Override
    protected void onPostExecute(Object o) {
        mProgressDialog.dismiss();
    }

    private void initProgressDialog(int titleRes, int messageRes) {
        mProgressDialog = new ProgressDialog(mActivity);
        mProgressDialog.setTitle(mActivity.getString(titleRes));
        mProgressDialog.setMessage(mActivity.getString(messageRes));
        mProgressDialog.setIndeterminate(true);
    }

    protected abstract void initAdapterListView();

}
