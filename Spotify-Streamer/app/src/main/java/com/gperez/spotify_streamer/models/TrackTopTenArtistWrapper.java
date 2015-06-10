package com.gperez.spotify_streamer.models;

import java.io.Serializable;

/**
 * Created by gabriel on 6/7/2015.
 */
public class TrackTopTenArtistWrapper implements Serializable {
    private String trackName;
    private String albumName;
    private String albumArtThumbnail;
    private String previewUrl;

    public TrackTopTenArtistWrapper() {
    }

    public TrackTopTenArtistWrapper(String trackName, String albumName, String albumArtThumbnail, String previewUrl) {
        this.trackName = trackName;
        this.albumName = albumName;
        this.albumArtThumbnail = albumArtThumbnail;
        this.previewUrl = previewUrl;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
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
}
