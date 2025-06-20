package com.mmcneil.musicstore.model;

public class Track implements Release {
    private String id;
    private String title;
    private Artist artist;
    private Album album;
    private int duration;
    private String preview;


    @Override
    public String getId() { return id; }

    @Override
    public String getTitle() { return title; }

    @Override
    public String getArtist() { return artist != null ? artist.getName() : ""; }

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
