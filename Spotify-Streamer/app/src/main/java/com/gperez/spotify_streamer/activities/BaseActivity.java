package com.gperez.spotify_streamer.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by gabriel on 6/10/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Intent getParentActivityIntent() {
        return super.getParentActivityIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    public Bundle getExtras() {
        return getIntent().getExtras();
    }
}
