package com.gperez.spotify_streamer.activities;

import android.os.Bundle;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.fragments.TopTenTracksFragment;
import com.gperez.spotify_streamer.models.ArtistWrapper;

public class TopTenTracksActivity extends BaseManagerAsyncTaskActivity {
    private static final String SUBTITLE_INSTANCE_STATE_KEY = "subtitle-instance-state";
    public static final String ARG_ARTIST = "arg-artist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten_tracks);

        if (savedInstanceState == null) {
            ArtistWrapper artist = (ArtistWrapper)getIntent().getExtras().getSerializable(ARG_ARTIST);
            getSupportActionBar().setSubtitle(artist.getName());

            getSupportFragmentManager().beginTransaction().add(R.id.container, TopTenTracksFragment.create(artist)).commit();
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
