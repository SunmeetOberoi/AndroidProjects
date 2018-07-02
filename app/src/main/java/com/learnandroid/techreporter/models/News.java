package com.learnandroid.techreporter.models;

import java.util.Date;

public class News {

    String author;
    String title;
    String Description;
    String path_to_news;
    String path_to_image;
    Date date;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPath_to_news() {
        return path_to_news;
    }

    public void setPath_to_news(String path_to_news) {
        this.path_to_news = path_to_news;
    }

    public String getPath_to_image() {
        return path_to_image;
    }

    public void setPath_to_image(String path_to_image) {
        this.path_to_image = path_to_image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
