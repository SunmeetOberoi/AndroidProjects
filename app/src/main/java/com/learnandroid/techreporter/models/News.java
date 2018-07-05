package com.learnandroid.techreporter.models;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

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
        String input_date = "";
        //Separate Date
        for (int i=0;i < date_time.length()-1;i++) {
            if(date_time.charAt(i) == 'T'){
                input_date = input_date + " ";
                continue;
            }
            input_date = input_date + date_time.charAt(i);
        }
        try {
            SimpleDateFormat input_format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            input_format.setTimeZone(TimeZone.getTimeZone("UTC"));//Source TimeZone
            Date parsed = input_format.parse(input_date);

            TimeZone zone =  TimeZone.getDefault();//Fetching local timezone
            SimpleDateFormat output_format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            output_format.setTimeZone(zone);// Local TimeZone
            input_date = output_format.format(parsed);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.date_time = input_date;

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
