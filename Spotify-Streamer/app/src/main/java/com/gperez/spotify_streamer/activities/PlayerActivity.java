package com.gperez.spotify_streamer.activities;

import android.os.Bundle;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.fragments.PlayerFragment;
import com.gperez.spotify_streamer.models.TrackTopTenArtistWrapper;

import java.util.List;


public class PlayerActivity extends BaseActivity {
    public static final String ARG_TOP_TEN_TRACKS = "arg-top-ten-tracks";
    public static final String ARG_POSITION_TRACK_LIST = "arg-position-track-list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            List<TrackTopTenArtistWrapper> topTenTrackList =
                    (List<TrackTopTenArtistWrapper>) extras.getSerializable(ARG_TOP_TEN_TRACKS);

            int positionList = extras.getInt(ARG_POSITION_TRACK_LIST);


            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, PlayerFragment.create(topTenTrackList,positionList))
                    .commit();
        }
    }

}
