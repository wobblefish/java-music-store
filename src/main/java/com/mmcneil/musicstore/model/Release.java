package com.mmcneil.musicstore.model;

public interface Release {
    String getId();
    String getTitle();
    String getArtist();
    String getCoverSmall();
    String getCoverMedium();
    String getCoverBig();
    String getCoverXl();
    String getPictureSmall();
    String getPictureMedium();
    String getPictureBig();
    String getPictureXl();
    String getType(); // "album" or "track"
}
