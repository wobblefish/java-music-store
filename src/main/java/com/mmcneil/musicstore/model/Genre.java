package com.mmcneil.musicstore.model;

import com.google.gson.annotations.SerializedName;

public class Genre {
    private int id;
    private String name;
    private String picture;
    @SerializedName("picture_small")
    private String pictureSmall;
    @SerializedName("picture_medium")
    private String pictureMedium;
    @SerializedName("picture_big")
    private String pictureBig;
    @SerializedName("picture_xl")
    private String pictureXl;
    
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPicture() { return picture; }
    public String getPictureSmall() { return pictureSmall; }
    public String getPictureMedium() { return pictureMedium; }
    public String getPictureBig() { return pictureBig; }
    public String getPictureXl() { return pictureXl; }
}


