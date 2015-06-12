package com.gperez.spotify_streamer.services;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * Created by gabriel on 6/11/2015.
 */
public class MediaPlayerService implements MediaPlayer.OnPreparedListener {
    private static final String LOG_CAT = MediaPlayerService.class.getSimpleName();
    private static MediaPlayerService mInstance;
    private MediaPlayer mMediaPlayer;
    private String trackUrl;

    public static MediaPlayerService getInstance() {
        if (mInstance == null) {
            mInstance = new MediaPlayerService();
        }

        return mInstance;
    }

    public void initMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public void setTrackUrl(String trackUrl){
        this.trackUrl = trackUrl;
    }

    public MediaPlayer getmMediaPlayer(){
        return mMediaPlayer;
    }

    public void play() {
        if (trackUrl == null) {
            return;
        }

        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(trackUrl);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepareAsync();
        } catch (IllegalStateException e) {
            Log.e(LOG_CAT, e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            Log.e(LOG_CAT, e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(LOG_CAT, e.getMessage());
            e.printStackTrace();
        }
    }

    public void replay() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        }
    }

    public void pause() {
        if (mMediaPlayer == null || !mMediaPlayer.isPlaying()) {
            return;
        }

        mMediaPlayer.pause();
    }

    public  void stop(){
        if (mMediaPlayer == null || !mMediaPlayer.isPlaying()) {
            return;
        }

        mMediaPlayer.stop();
    }

    public boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }

    public int getDuration() {
        if (mMediaPlayer == null) {
            return 0;
        }
        return mMediaPlayer.getDuration();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }
}
