package com.gperez.spotify_streamer.models;

import java.io.Serializable;

/**
 * Created by gabriel on 6/7/2015.
 */
public class ArtistWrapper implements Serializable {
    private String spotifyId;
    private String name;
    private String thumbnailImage;

    public ArtistWrapper(){}

    public ArtistWrapper(String spotifyId, String name, String thumbnailImage) {
        this.spotifyId = spotifyId;
        this.name = name;
        this.thumbnailImage = thumbnailImage;
    }

    public String getSpotifyId() {
        return spotifyId;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }
}
