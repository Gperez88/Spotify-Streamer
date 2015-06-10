package com.gperez.spotify_streamer.tasks;

import android.view.View;

import com.gperez.spotify_streamer.services.SpotifyApi;
import com.gperez.spotify_streamer.utils.CustomAsyncTask;

import kaaes.spotify.webapi.android.SpotifyService;

/**
 * Created by gabriel on 5/30/2015.
 */
public abstract class BaseSearchAsyncTask extends CustomAsyncTask {
    protected AsyncTaskParams asyncTaskParams;
    protected SpotifyService spotifyService;

    public BaseSearchAsyncTask(AsyncTaskParams mAsyncTaskParams) {
        super(mAsyncTaskParams.getActivity());
        this.asyncTaskParams = mAsyncTaskParams;
        this.spotifyService = SpotifyApi.getInstance(mAsyncTaskParams.getActivity()).getService();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        initAdapterListView();

        if(asyncTaskParams.isSearchFragment()){
            asyncTaskParams.getLinearLayout().setVisibility(View.GONE);
            asyncTaskParams.getProgressBar().setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        asyncTaskParams.getLinearLayout().setVisibility(View.VISIBLE);
        asyncTaskParams.getProgressBar().setVisibility(View.GONE);

    }

    protected abstract void initAdapterListView();

}
