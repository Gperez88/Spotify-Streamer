package com.gperez.spotify_streamer.activities;

import android.os.Bundle;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.fragments.TopTenTracksFragment;

public class TopTenTracksActivity extends BaseManagerAsyncTaskActivity {
    private static final String SUBTITLE_INSTANCE_STATE_KEY = "subtitle-instance-state";
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

            getSupportFragmentManager().beginTransaction().add(R.id.container, TopTenTracksFragment.create(artistId)).commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(SUBTITLE_INSTANCE_STATE_KEY, getSupportActionBar().getSubtitle().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        getSupportActionBar().setSubtitle(savedInstanceState.getString(SUBTITLE_INSTANCE_STATE_KEY, ""));
    }
}
