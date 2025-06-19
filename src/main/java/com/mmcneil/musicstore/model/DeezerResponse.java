package com.mmcneil.musicstore.model;
import java.util.List;

public class DeezerResponse<T> {
    private List<T> data;

    public List<T> getData() {
        return data;
    }
}
