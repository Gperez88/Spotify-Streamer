package com.gperez.spotify_streamer.services;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gperez.spotify_streamer.R;

import kaaes.spotify.webapi.android.SpotifyService;

/**
 * Created by gabriel on 5/30/2015.
 */
public class SpotifyApi {
    private static SpotifyApi mInstance;
    private kaaes.spotify.webapi.android.SpotifyApi mSpotifyApi;

    public static SpotifyApi getInstance(Activity mActivity) {
        if (mInstance == null) {
            mInstance = new SpotifyApi(mActivity);
        }

        return mInstance;
    }

    private SpotifyApi(Activity mActivity) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
        String prefAccessTokenKey = mActivity.getString(R.string.pref_access_token);
        String prefAccessToken = prefs.getString(prefAccessTokenKey, null);

        mSpotifyApi = new kaaes.spotify.webapi.android.SpotifyApi();
        mSpotifyApi.setAccessToken(prefAccessToken);
    }

    public SpotifyService getService(){
       return mSpotifyApi.getService();
    }
}
