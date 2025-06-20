package com.mmcneil.musicstore.model;

import com.google.gson.annotations.SerializedName;

public class Album implements Release {
    private String title;
    private Artist artist;
    private String coverUrl;
    @SerializedName("cover_medium")
    private String coverMedium;
    private String id;

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

    @Override
    public String getType() { return "album"; }

    public String getCoverUrl() { return coverUrl; }

    public double getPrice() { return price; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public void setPrice(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return title + " by " + artist;
    }
}
