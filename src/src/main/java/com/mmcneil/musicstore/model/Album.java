package src.main.java.com.mmcneil.musicstore.model;

public class Album {
    private String title;
    private String artist;
    private String coverUrl;
    private String id;

    private double price;
    private int quantity;

    public Album(String id, String title, String artist, String coverUrl) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.coverUrl = coverUrl;
        this.price = 9.99; // default for now
        this.quantity = 1;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getCoverUrl() { return coverUrl; }
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
