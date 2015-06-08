package com.gperez.spotify_streamer.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.fragments.TopTenTracksFragment;
import com.gperez.spotify_streamer.utils.CustomApplication;

public class TopTenTracksActivity extends AppCompatActivity {
    public static final String ARG_ARTIST_NAME = "arg-artist-name";
    public static final String ARG_ARTIST_ID = "arg-artist-id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten_tracks);

        if (savedInstanceState == null) {
            String artistId = getIntent().getExtras().getString(ARG_ARTIST_ID);
            String artistName = getIntent().getExtras().getString(ARG_ARTIST_NAME);

            getSupportActionBar().setSubtitle(artistName);

            TopTenTracksFragment mTopTenTracksFragment = TopTenTracksFragment.create(artistId);

            getSupportFragmentManager().beginTransaction().add(R.id.container, mTopTenTracksFragment).commit();
        }
    }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top_ten_tracks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Intent getParentActivityIntent() {
        return super.getParentActivityIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }
}
