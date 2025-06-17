package com.shrinitha.dochub.dto;

import java.util.List;

public class SearchResponse {
    private List<String> filenames;

    public SearchResponse(List<String> filenames) {
        this.filenames = filenames;
    }

    public List<String> getFilenames() {
        return filenames;
    }

    public void setFilenames(List<String> filenames) {
        this.filenames = filenames;
    }
}
