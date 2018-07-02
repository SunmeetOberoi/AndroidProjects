package com.learnandroid.techreporter.models;

import android.util.Log;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class News {

    String author;
    String title;
    String Description;
    String path_to_news;
    String path_to_image;
    String date_time;

    public News(String author, String title, String description, String path_to_news, String path_to_image, String date_time) {
        this.author = author;
        this.title = title;
        Description = description;
        this.path_to_news = path_to_news;
        this.path_to_image = path_to_image;
        formatDateTime(date_time);
    }

    private void formatDateTime(String date_time) {
        this.date_time="";
        int i;
        for (i=0;date_time.charAt(i)!='T';i++)
            this.date_time = this.date_time + date_time.charAt(i);
        this.date_time=this.date_time + " ";
        i++;
        for (;date_time.charAt(i)!='Z';i++){
            this.date_time = this.date_time + date_time.charAt(i);
        }
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return Description;
    }

    public String getPath_to_news() {
        return path_to_news;
    }

    public String getPath_to_image() {
        return path_to_image;
    }

    public String getDate_time() {
        return date_time;
    }
}
