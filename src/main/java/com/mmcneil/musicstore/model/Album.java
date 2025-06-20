package com.mmcneil.musicstore.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Album implements Release {
    private String id;
    private String title;
    private Artist artist;
    private String coverUrl;
    @SerializedName("cover_small")
    private String coverSmall;
    @SerializedName("cover_medium")
    private String coverMedium;
    @SerializedName("cover_big")
    private String coverBig;
    @SerializedName("cover_xl")
    private String coverXl;
    @SerializedName("picture_small")
    private String pictureSmall;
    @SerializedName("picture_medium")
    private String pictureMedium;
    @SerializedName("picture_big")
    private String pictureBig;
    @SerializedName("picture_xl")
    private String pictureXl;
    @SerializedName("genre_id")
    private int genreId;
    private List<Genre> genres;
    private String label;
    @SerializedName("nb_tracks")
    private int nbTracks;
    private int duration;
    private int fans;
    @SerializedName("release_date")
    private String releaseDate;
    private String tracklist;
    @SerializedName("explicit_lyrics")
    private boolean explicitLyrics;
    @SerializedName("explicit_content_lyrics")
    private int explicitContentLyrics;
    @SerializedName("explicit_content_cover")
    private int explicitContentCover;
    private List<Artist> contributors;
    private List<Track> tracks;

    private double price;
    private int quantity;
    
    public Album(String id, String title, Artist artist, String coverUrl) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.coverUrl = coverUrl;
        this.price = 9.99; // default for now
        this.quantity = 1;
    }

    @Override
    public String getId() { return id; }

    @Override
    public String getTitle() { return title; }

    @Override
    public String getArtist() { return artist != null ? artist.getName() : ""; }

    @Override
    public String getCoverSmall() { return coverUrl; }
    @Override
    public String getCoverMedium() { return coverMedium; }
    @Override
    public String getCoverBig() { return coverMedium; }
    @Override
    public String getCoverXl() { return coverMedium; }
    @Override
    public String getPictureSmall() { return coverMedium; }
    @Override
    public String getPictureMedium() { return coverMedium; }
    @Override
    public String getPictureBig() { return coverMedium; }
    @Override
    public String getPictureXl() { return coverMedium; }

    public int getDuration() { return duration; }

    public int getNbTracks() { return nbTracks; }

    public String getLabel() { return label; }

    @Override
    public String getType() { return "album"; }

    public String getCoverUrl() { return coverUrl; }

    public String getTracklist() { return tracklist; }

    public double getPrice() { return price; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public void setPrice(int quantity) { this.quantity = quantity; }

    public List<Artist> getContributors() { return contributors; }

    public List<Track> getTracks() { return tracks; }

    public List<Genre> getGenres() { return genres; }


    @Override
    public String toString() {
        return title + " by " + artist;
    }
}
