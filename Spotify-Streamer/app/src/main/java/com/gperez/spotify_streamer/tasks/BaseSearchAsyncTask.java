package com.gperez.spotify_streamer.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.gperez.spotify_streamer.services.SpotifyApi;
import com.gperez.spotify_streamer.utils.CustomAsyncTask;

import kaaes.spotify.webapi.android.SpotifyService;

/**
 * Created by gabriel on 5/30/2015.
 */
public abstract class BaseSearchAsyncTask extends CustomAsyncTask {
    protected Activity mActivity;
    protected ProgressDialog mProgressDialog;
    protected SpotifyService mSpotifyService;

    public BaseSearchAsyncTask(Activity mActivity, int titleProgressBarRes, int messageProgressBarRes) {
        super(mActivity);
        this.mActivity = mActivity;
        this.mSpotifyService = SpotifyApi.getInstance(mActivity).getService();

        //initProgressDialog(titleProgressBarRes, messageProgressBarRes);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       // mProgressDialog.show();
        initAdapterListView();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        //mProgressDialog.dismiss();
    }

    private void initProgressDialog(int titleRes, int messageRes) {
        mProgressDialog = new ProgressDialog(mActivity);
        mProgressDialog.setTitle(mActivity.getString(titleRes));
        mProgressDialog.setMessage(mActivity.getString(messageRes));
        mProgressDialog.setIndeterminate(true);
    }

    protected abstract void initAdapterListView();

}
