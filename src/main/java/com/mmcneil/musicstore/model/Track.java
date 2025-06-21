package com.mmcneil.musicstore.model;

import com.google.gson.annotations.SerializedName;

public class Track implements Release {
    private int id;
    private String title;
    private Artist artist;
    private Album album;
    private int duration;
    @SerializedName("track_position")
    private int trackPosition;
    private String preview;


    @Override
    public int getId() { return id; }

    @Override
    public String getTitle() { return title; }

    @Override
    public String getArtist() { return artist != null ? artist.getName() : ""; }

    public int getDuration() { return duration; }
    
    public int getTrackPosition() { return trackPosition; }

    public String getPaddedTrackPosition() {
        return String.format("%02d", trackPosition);
    }

    public String getPreview() { return preview; }

    @Override
    public String getCoverSmall() { return album != null ? album.getCoverSmall() : ""; }
    @Override
    public String getCoverMedium() { return album != null ? album.getCoverMedium() : ""; }
    @Override
    public String getCoverBig() { return album != null ? album.getCoverBig() : ""; }
    @Override
    public String getCoverXl() { return album != null ? album.getCoverXl() : ""; }
    @Override
    public String getPictureSmall() { return album != null ? album.getPictureSmall() : ""; }
    @Override
    public String getPictureMedium() { return album != null ? album.getPictureMedium() : ""; }
    @Override
    public String getPictureBig() { return album != null ? album.getPictureBig() : ""; }

    @Override
    public String getPictureXl() { return album != null ? album.getPictureXl() : ""; }
    @Override
    public String getType() { return "track"; }

    public Album getAlbum() {
        return album;
    }
}
