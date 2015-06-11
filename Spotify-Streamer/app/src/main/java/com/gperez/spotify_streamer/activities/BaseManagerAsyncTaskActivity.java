package com.gperez.spotify_streamer.activities;

import android.os.Bundle;

import com.gperez.spotify_streamer.utils.CustomApplication;

/**
 * Created by gabriel on 6/9/2015.
 */
public abstract class BaseManagerAsyncTaskActivity extends BaseActivity {

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        ((CustomApplication) getApplication()).detach(this);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        ((CustomApplication) getApplication()).attach(this);
    }


}
