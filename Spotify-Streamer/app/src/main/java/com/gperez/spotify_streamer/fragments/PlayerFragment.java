package com.gperez.spotify_streamer.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.activities.PlayerActivity;
import com.gperez.spotify_streamer.models.TrackTopTenArtistWrapper;
import com.squareup.picasso.Picasso;

public class PlayerFragment extends BaseFragment {
    TrackTopTenArtistWrapper trackTopTenArtistWrapper;

    private TextView artistNamePlayer;
    private TextView albumNamePlayer;
    private TextView trackNamePlayer;
    private ImageView albumArtworkPlayer;

    public static PlayerFragment create(TrackTopTenArtistWrapper trackTopTenArtistWrapper) {
        PlayerFragment playerFragment = new PlayerFragment();

        Bundle args = new Bundle();
        args.putSerializable(PlayerActivity.ARG_TOP_TEN_TRACK, trackTopTenArtistWrapper);
        playerFragment.setArguments(args);

        return playerFragment;
    }

    public PlayerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player, container, false);
    }

    @Override
    protected void initComponents() {
        trackTopTenArtistWrapper = (TrackTopTenArtistWrapper)
                getArguments().getSerializable(PlayerActivity.ARG_TOP_TEN_TRACK);

        artistNamePlayer = (TextView) getView().findViewById(R.id.artist_name_player_textview);
        albumNamePlayer = (TextView) getView().findViewById(R.id.album_name_player_textview);
        trackNamePlayer = (TextView) getView().findViewById(R.id.track_name_player_textview);
        albumArtworkPlayer = (ImageView) getView().findViewById(R.id.album_artwork_player_imageview);

        artistNamePlayer.setText(trackTopTenArtistWrapper.getArtist().getName());
        albumNamePlayer.setText(trackTopTenArtistWrapper.getAlbumName());
        trackNamePlayer.setText(trackTopTenArtistWrapper.getTrackName());

        Picasso.with(getActivity())
                .load(trackTopTenArtistWrapper.getAlbumArtThumbnail())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .resizeDimen(R.dimen.thumbnail_single_player_width, R.dimen.thumbnail_single_player_height)
                .centerCrop()
                .into(albumArtworkPlayer);
    }
}
