package com.gperez.spotify_streamer.models;

import java.io.Serializable;

/**
 * Created by gabriel on 6/7/2015.
 */
public class TrackTopTenArtistWrapper implements Serializable {
    private String trackName;
    private long trackDuration;
    private String albumName;
    private String albumArtThumbnail;
    private String previewUrl;
    private ArtistWrapper artist;

    public TrackTopTenArtistWrapper() {
    }

    public TrackTopTenArtistWrapper(String trackName, long trackDuration, String albumName, String albumArtThumbnail, String previewUrl, ArtistWrapper artist) {
        this.trackName = trackName;
        this.trackDuration = trackDuration;
        this.albumName = albumName;
        this.albumArtThumbnail = albumArtThumbnail;
        this.previewUrl = previewUrl;
        this.artist = artist;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public long getTrackDuration() {
        return trackDuration;
    }

    public void setTrackDuration(long trackDuration) {
        this.trackDuration = trackDuration;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumArtThumbnail() {
        return albumArtThumbnail;
    }

    public void setAlbumArtThumbnail(String albumArtThumbnail) {
        this.albumArtThumbnail = albumArtThumbnail;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public ArtistWrapper getArtist() {
        return artist;
    }

    public void setArtist(ArtistWrapper artist) {
        this.artist = artist;
    }
}
