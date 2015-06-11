package com.gperez.spotify_streamer.activities;

import android.os.Bundle;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.fragments.PlayerFragment;
import com.gperez.spotify_streamer.models.TrackTopTenArtistWrapper;


public class PlayerActivity extends BaseActivity {
    public static final String ARG_TOP_TEN_TRACK = "arg-top-ten-track";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        if (savedInstanceState == null) {
            TrackTopTenArtistWrapper trackTopTenArtistWrapper =
                    (TrackTopTenArtistWrapper) getIntent().getExtras().getSerializable(ARG_TOP_TEN_TRACK);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, PlayerFragment.create(trackTopTenArtistWrapper))
                    .commit();
        }
    }

}
