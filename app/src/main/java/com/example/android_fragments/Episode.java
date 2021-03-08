package com.example.android_fragments;

import java.util.List;

public class Episode {
    private String episode;
    private String name;
    private String airDate;
    private List<String> charImgs;
    private String url;
    private String title;

    public Episode() {

    }

    public Episode(String episode, String name, String airDate, List<String> charImgs) {
        this.episode = episode;
        this.name = name;
        this.airDate = airDate;
        this.charImgs = charImgs;
        this.url = "https://rickandmorty.fandom.com/wiki/" + name.replace(" ", "_");
        this.title = episode + ": " + name;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public List<String> getCharImgs() {
        return charImgs;
    }

    public void setCharImgs(List<String> charImgs) {
        this.charImgs = charImgs;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String episode, String name) {
        this.name = episode + ": " + name;
    }
}