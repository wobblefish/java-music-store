package src.main.java.com.mmcneil.musicstore.api;

import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import src.main.java.com.mmcneil.musicstore.model.Album;
import src.main.java.com.mmcneil.musicstore.model.DeezerResponse;
import src.main.java.com.mmcneil.musicstore.model.DeezerTrack;

import java.util.ArrayList;
import java.util.List;

public class DeezerClient {
    public static List<Album> getSearchResults(String searchTerm) {
        List<Album> albumList = new ArrayList<>();
        String url = "https://api.deezer.com/search?q=" + searchTerm;
        HttpResponse<String> response = Unirest.get(url).asString();
        String json = response.getBody();
        Gson gson = new Gson();
        DeezerResponse result = gson.fromJson(json, DeezerResponse.class);

        for (DeezerTrack track : result.getData()) {
            Album album = new Album(
                    track.getId(),
                    track.getTitle(),
                    track.getArtist().getName(),
                    track.getAlbum().getCover_medium()
            );
            albumList.add(album);
        }

        return albumList;
    }
}
