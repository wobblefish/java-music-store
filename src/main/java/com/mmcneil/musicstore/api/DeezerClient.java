package com.mmcneil.musicstore.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mmcneil.musicstore.model.Album;
import com.mmcneil.musicstore.model.DeezerResponse;
import com.mmcneil.musicstore.model.Track;
import com.mmcneil.musicstore.model.Tracklist;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.util.ArrayList;
import java.util.List;

public class DeezerClient {
    public static List<Track> getTrackSearchResults(String searchTerm) {
        List<Track> trackList = new ArrayList<>();
        String url = "https://api.deezer.com/search/track?q=" + searchTerm;
        HttpResponse<String> response = Unirest.get(url).asString();
        String json = response.getBody();
        Gson gson = new Gson();
        DeezerResponse<Track> result = gson.fromJson(json, new TypeToken<DeezerResponse<Track>>(){}.getType());

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

    public static List<Track> getAlbumTracks(String tracklistUrl) {
        List<Track> tracks = new ArrayList<>();
        HttpResponse<String> response = Unirest.get(tracklistUrl).asString();
        String json = response.getBody();
        Gson gson = new Gson();
        Tracklist tracklist = gson.fromJson(json, Tracklist.class);
        if (tracklist != null && tracklist.getData() != null) {
            tracks.addAll(tracklist.getData());
        }
        return tracks;
    }

    public static Album getAlbumDetails(long albumId) {
        String url = "https://api.deezer.com/album/" + albumId;
        HttpResponse<String> response = Unirest.get(url).asString();
        System.out.println("=== RAW JSON ===");
        System.out.println(response.getBody());
        System.out.println("=== END RAW JSON ===");
        Gson gson = new Gson();
        return gson.fromJson(response.getBody(), Album.class);
    }
}
