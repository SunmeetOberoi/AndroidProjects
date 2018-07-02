package com.learnandroid.techreporter.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.learnandroid.techreporter.R;
import com.learnandroid.techreporter.adapters.RecyclerViewAdapter;
import com.learnandroid.techreporter.models.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    List<News> news;
    @BindView(R.id.rvNews)
    RecyclerView rvNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeData();

        LinearLayoutManager llm = new LinearLayoutManager(this);

        rvNews.setHasFixedSize(true);
        rvNews.setLayoutManager(llm);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,news);

        rvNews.setAdapter(adapter);


    }

    private void initializeData() {
        news = new ArrayList<>();
        final boolean[] stop = {true};
        final JSONObject[] object = new JSONObject[1];
        Thread Json = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = new URL("https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=49d0e3d467744d8d8185bf84162ec9d6").openStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();
                    int ch;
                    while((ch=br.read()) != -1){
                        sb.append((char) ch);
                    }
                    is.close();
                    object[0] = new JSONObject(sb.toString());
                    stop[0] =false;
                } catch (Exception e){
                    Log.e("URL",e.getMessage());
                }
            }
        });Json.start();

        while(stop[0]);

        try {
            JSONArray articles = object[0].getJSONArray("articles");
            for (int i=0,size=articles.length();i<size;i++){
                JSONObject obj = articles.getJSONObject(i);
                news.add(new News(obj.getString("author"),obj.getString("title"),obj.getString("description"),obj.getString("url"),obj.getString("urlToImage"),obj.getString("publishedAt")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
