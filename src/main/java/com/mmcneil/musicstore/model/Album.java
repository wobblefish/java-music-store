package com.mmcneil.musicstore.model;

public class Album {
    private String title;
    private DeezerArtist artist;
    private String coverUrl;
    private String cover_medium;
    private String id;

    private double price;
    private int quantity;

    public Album(String id, String title, DeezerArtist artist, String coverUrl) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.coverUrl = coverUrl;
        this.price = 9.99; // default for now
        this.quantity = 1;
    }

    public String getTitle() { return title; }
    public DeezerArtist getArtist() { return artist; }
    public String getCoverUrl() { return coverUrl; }
    public String getCover_medium() { return cover_medium; }
    public String getId() { return id; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return title + " by " + artist;
    }
}
