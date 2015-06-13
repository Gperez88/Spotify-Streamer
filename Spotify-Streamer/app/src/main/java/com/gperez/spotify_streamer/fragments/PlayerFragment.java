package com.gperez.spotify_streamer.fragments;

import android.media.AudioManager;
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
import com.gperez.spotify_streamer.utils.Utils;
import com.squareup.okhttp.internal.Util;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class PlayerFragment extends BaseFragment implements View.OnClickListener, MediaPlayer.OnPreparedListener{
    private static final String CURRENT_POSITION_TRACK = "current-position-track";
    private int lastPositionSaveInstance = -1;

    private List<TrackTopTenArtistWrapper> topTenTrackList;
    private TrackTopTenArtistWrapper trackTopTenArtist;

    private int firstPositionList = 0;
    private int lastPositionList;
    private int currentPositionList = -1;

    private TextView artistNamePlayer;
    private TextView albumNamePlayer;
    private TextView trackNamePlayer;
    private ImageView albumArtworkPlayer;

    private SeekBar progressSeekBar;

    private TextView progressTrack;
    private TextView durationTrack;

    private ImageButton previousButton;
    private ImageButton nextButton;
    private ImageButton playButton;

    private MediaPlayer mediaPlayer;

    private Handler mHandler = new Handler();

    public static PlayerFragment create(List<TrackTopTenArtistWrapper> topTenTrackList, int currentPositionList) {
        PlayerFragment playerFragment = new PlayerFragment();

        Bundle args = new Bundle();
        args.putSerializable(PlayerActivity.ARG_TOP_TEN_TRACKS, (Serializable) topTenTrackList);
        args.putInt(PlayerActivity.ARG_POSITION_TRACK_LIST, currentPositionList);
        playerFragment.setArguments(args);

        return playerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            lastPositionSaveInstance = savedInstanceState.getInt(CURRENT_POSITION_TRACK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(CURRENT_POSITION_TRACK, currentPositionList);
    }


    @Override
    protected void initComponents() {
        topTenTrackList = (List<TrackTopTenArtistWrapper>)
                getArguments().getSerializable(PlayerActivity.ARG_TOP_TEN_TRACKS);

        currentPositionList = lastPositionSaveInstance >= 0 ? lastPositionSaveInstance :
                getArguments().getInt(PlayerActivity.ARG_POSITION_TRACK_LIST);

        lastPositionList = topTenTrackList.size() - 1;

        artistNamePlayer = (TextView) getView().findViewById(R.id.artist_name_player_textview);
        albumNamePlayer = (TextView) getView().findViewById(R.id.album_name_player_textview);
        trackNamePlayer = (TextView) getView().findViewById(R.id.track_name_player_textview);
        albumArtworkPlayer = (ImageView) getView().findViewById(R.id.album_artwork_player_imageview);

        progressTrack = (TextView) getView().findViewById(R.id.progress_track_textview);
        durationTrack = (TextView) getView().findViewById(R.id.duration_track_textview);

        progressSeekBar = (SeekBar) getView().findViewById(R.id.progress_player_seekbar);

        nextButton = (ImageButton) getView().findViewById(R.id.next_button);
        previousButton = (ImageButton) getView().findViewById(R.id.previous_button);
        playButton = (ImageButton) getView().findViewById(R.id.play_button);

        nextButton.setOnClickListener(this);
        previousButton.setOnClickListener(this);
        playButton.setOnClickListener(this);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);

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
                if (mediaPlayer != null) {

                    if (mediaPlayer.isPlaying()) {
                        playButton.setImageResource(R.mipmap.ic_media_play);
                        mediaPlayer.pause();
                    } else {
                        playButton.setImageResource(R.mipmap.ic_media_pause);
                        mediaPlayer.start();
                    }

                }
                break;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        relaxMediaPlayer(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        relaxMediaPlayer(true);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    private void nextTrack() {
        if (currentPositionList < lastPositionList) {
            currentPositionList++;

            mediaPlayer.stop();
            prepareTrackPlayer(currentPositionList);
        }
    }

    private void previousTrack() {
        if (currentPositionList > firstPositionList) {
            currentPositionList--;

            mediaPlayer.stop();
            prepareTrackPlayer(currentPositionList);
        }
    }

    private void playTrack(String trackUrl) {
        if (trackUrl == null) {
            return;
        }

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(trackUrl);

            mediaPlayer.prepareAsync();

            playButton.setImageResource(R.mipmap.ic_media_pause);

            progressSeekBar.setProgress(0);
            progressSeekBar.setMax(100);

            updateProgressSeekBar();

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateProgressSeekBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();

            progressTrack.setText(Utils.milliSecondsToTimer(currentDuration));
            durationTrack.setText(Utils.milliSecondsToTimer(totalDuration));

            int progress = (Utils.getProgressPercentage(currentDuration, totalDuration));
            progressSeekBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };

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

        playTrack(trackTopTenArtist.getPreviewUrl());
    }

    private void relaxMediaPlayer(boolean releaseMediaPlayer) {

        if (releaseMediaPlayer && mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;

            mHandler.removeCallbacks(mUpdateTimeTask);
        }
    }
}
