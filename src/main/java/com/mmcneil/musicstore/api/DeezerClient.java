package com.mmcneil.musicstore.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mmcneil.musicstore.model.Album;
import com.mmcneil.musicstore.model.DeezerResponse;
import com.mmcneil.musicstore.model.DeezerTrack;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.util.ArrayList;
import java.util.List;

public class DeezerClient {
    public static List<DeezerTrack> getTrackSearchResults(String searchTerm) {
        List<DeezerTrack> trackList = new ArrayList<>();
        String url = "https://api.deezer.com/search/track?q=" + searchTerm;
        HttpResponse<String> response = Unirest.get(url).asString();
        String json = response.getBody();
        Gson gson = new Gson();
        DeezerResponse<DeezerTrack> result = gson.fromJson(json, new TypeToken<DeezerResponse<DeezerTrack>>(){}.getType());

        // Null check
        if (result.getData() != null) {
            trackList.addAll(result.getData());
        }

        return trackList;
    }

    public static List<Album> getAlbumSearchResults(String searchTerm) {
        List<Album> albumList = new ArrayList<>();
        String url = "https://api.deezer.com/search/album?q=" + searchTerm;
        HttpResponse<String> response = Unirest.get(url).asString();
        String json = response.getBody();
        Gson gson = new Gson();
        DeezerResponse<Album> result = gson.fromJson(json, new TypeToken<DeezerResponse<Album>>(){}.getType());
        if (result.getData() != null) {
            albumList.addAll(result.getData());
        }
        return albumList;
    }
}
