package com.learnandroid.tvseriestracker.model;

public class Series {

    private String title;
    private int season;
    private int episode;

    public Series(String title, int season, int episode) {
        this.title = title;
        this.season = season;
        this.episode = episode;
    }

    public String getTitle() {
        return title;
    }

    public int getSeason() {
        return season;
    }

    public int getEpisode() {
        return episode;
    }
}
