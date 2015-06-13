package com.gperez.spotify_streamer.activities;

import android.content.Intent;
import android.os.Bundle;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.fragments.PlayerFragment;
import com.gperez.spotify_streamer.fragments.TopTenTracksFragment.Callback;
import com.gperez.spotify_streamer.models.ArtistWrapper;
import com.gperez.spotify_streamer.models.TrackTopTenArtistWrapper;

import java.io.Serializable;
import java.util.List;

public class TopTenTracksActivity extends BaseManagerAsyncTaskActivity implements Callback {
    public static final String ARG_ARTIST = "arg_artist";
    private static final String PLAYER_FRAGMENT_TAG = "player_fragment_tag";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten_tracks);

        if (findViewById(R.id.player_container) != null) {

            mTwoPane = true;

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.player_container, new PlayerFragment(), PLAYER_FRAGMENT_TAG)
                        .commit();
            }

        } else {
            mTwoPane = false;
        }

        if (getExtras() != null) {
            getSupportActionBar().setSubtitle(((ArtistWrapper) getExtras().getSerializable(ARG_ARTIST)).getName());
        }
    }

    @Override
    public void onItemSelected(List<TrackTopTenArtistWrapper> topTenTrackList, int position) {
        if (mTwoPane) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.player_container, PlayerFragment.newInstance(topTenTrackList, position), PLAYER_FRAGMENT_TAG)
                    .commit();

        } else {
            Intent playerIntent = new Intent(this, PlayerActivity.class)
                    .putExtra(PlayerActivity.ARG_TOP_TEN_TRACKS, (Serializable) topTenTrackList)
                    .putExtra(PlayerActivity.ARG_POSITION_TRACK_LIST, position);

            startActivity(playerIntent);
        }
    }

    public boolean ismTwoPane() {
        return mTwoPane;
    }
}
