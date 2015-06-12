package com.gperez.spotify_streamer.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.activities.PlayerActivity;
import com.gperez.spotify_streamer.models.TrackTopTenArtistWrapper;
import com.gperez.spotify_streamer.services.MediaPlayerService;
import com.gperez.spotify_streamer.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class PlayerFragment extends BaseFragment implements View.OnClickListener {
    private List<TrackTopTenArtistWrapper> topTenTrackList;
    private TrackTopTenArtistWrapper trackTopTenArtist;

    private int firstPositionList = 0;
    private int lastPositionList;
    private int currentPositionList;

    private TextView artistNamePlayer;
    private TextView albumNamePlayer;
    private TextView trackNamePlayer;
    private ImageView albumArtworkPlayer;

    private TextView durationTrack;

    private SeekBar progressSeekBar;

    private ImageButton previousButton;
    private ImageButton nextButton;
    private ImageButton playButton;
    private ImageButton pauseButton;

    private Handler mHandler = new Handler();

    private boolean replay;

    public static PlayerFragment create(List<TrackTopTenArtistWrapper> topTenTrackList, int currentPositionList) {
        PlayerFragment playerFragment = new PlayerFragment();

        Bundle args = new Bundle();
        args.putSerializable(PlayerActivity.ARG_TOP_TEN_TRACKS, (Serializable) topTenTrackList);
        args.putInt(PlayerActivity.ARG_POSITION_TRACK_LIST, currentPositionList);
        playerFragment.setArguments(args);

        return playerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player, container, false);
    }

    @Override
    protected void initComponents() {
        MediaPlayerService.getInstance().initMediaPlayer();

        topTenTrackList = (List<TrackTopTenArtistWrapper>)
                getArguments().getSerializable(PlayerActivity.ARG_TOP_TEN_TRACKS);

        currentPositionList = getArguments().getInt(PlayerActivity.ARG_POSITION_TRACK_LIST);
        lastPositionList = topTenTrackList.size() - 1;

        artistNamePlayer = (TextView) getView().findViewById(R.id.artist_name_player_textview);
        albumNamePlayer = (TextView) getView().findViewById(R.id.album_name_player_textview);
        trackNamePlayer = (TextView) getView().findViewById(R.id.track_name_player_textview);
        albumArtworkPlayer = (ImageView) getView().findViewById(R.id.album_artwork_player_imageview);

        durationTrack = (TextView) getView().findViewById(R.id.duration_track_textview);

        progressSeekBar = (SeekBar) getView().findViewById(R.id.progress_player_seekbar);

        nextButton = (ImageButton) getView().findViewById(R.id.next_button);
        previousButton = (ImageButton) getView().findViewById(R.id.previous_button);
        playButton = (ImageButton) getView().findViewById(R.id.play_button);
        pauseButton = (ImageButton) getView().findViewById(R.id.pause_button);

        nextButton.setOnClickListener(this);
        previousButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);

        prepareTrackPlayer(currentPositionList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previous_button: {
                previousTrack();
                break;
            }
            case R.id.next_button: {
                nextTrack();
                break;
            }
            case R.id.play_button: {
                playTrack();
                break;
            }
            case R.id.pause_button: {
                pauseTrack();
                break;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        MediaPlayerService.getInstance().stop();

    }

    private void nextTrack() {
        if (currentPositionList < lastPositionList) {
            currentPositionList++;

            prepareTrackPlayer(currentPositionList);
        }
    }

    private void previousTrack() {
        if (currentPositionList > firstPositionList) {
            currentPositionList--;

            prepareTrackPlayer(currentPositionList);
        }
    }

    private void playTrack() {
        playButton.setVisibility(View.GONE);
        pauseButton.setVisibility(View.VISIBLE);

        if (replay) {
            MediaPlayerService.getInstance().replay();
        } else {
            MediaPlayerService.getInstance().play();
        }

        progressSeekBar.setProgress(0);
        progressSeekBar.setMax(100);

        updateProgressSeekBar();
    }

    public void updateProgressSeekBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = MediaPlayerService.getInstance().getmMediaPlayer().getDuration();
            long currentDuration = MediaPlayerService.getInstance().getmMediaPlayer().getCurrentPosition();

            int progress = (Utils.getProgressPercentage(currentDuration, totalDuration));

            progressSeekBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };

    private void pauseTrack() {
        playButton.setVisibility(View.VISIBLE);
        pauseButton.setVisibility(View.GONE);
        MediaPlayerService.getInstance().pause();
        replay = true;
    }

    private void prepareTrackPlayer(int currentPositionList) {
        trackTopTenArtist = topTenTrackList.get(currentPositionList);

        artistNamePlayer.setText(trackTopTenArtist.getArtist().getName());
        albumNamePlayer.setText(trackTopTenArtist.getAlbumName());
        trackNamePlayer.setText(trackTopTenArtist.getTrackName());

        Picasso.with(getActivity())
                .load(trackTopTenArtist.getAlbumArtThumbnail())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .resizeDimen(R.dimen.thumbnail_single_player_width, R.dimen.thumbnail_single_player_height)
                .centerCrop()
                .into(albumArtworkPlayer);

        MediaPlayerService.getInstance().setTrackUrl(trackTopTenArtist.getPreviewUrl());

//        durationTrack.setText(Utils.milliSecondsToTimer(
//                MediaPlayerService.getInstance().getmMediaPlayer().getDuration()).toString());

        if (MediaPlayerService.getInstance().isPlaying()) {
            pauseTrack();
        }
        replay = false;
    }

}
