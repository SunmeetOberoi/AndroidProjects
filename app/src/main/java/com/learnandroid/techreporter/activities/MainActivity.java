package com.learnandroid.techreporter.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

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
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    List<News> news = new ArrayList<>();
    @BindView(R.id.rvNews)
    RecyclerView rvNews;
    RecyclerViewAdapter adapter;
    ImageButton credit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getNews get_news = new getNews();
        get_news.execute();

        LinearLayoutManager llm = new LinearLayoutManager(this);

        rvNews.setHasFixedSize(true);
        rvNews.setLayoutManager(llm);

        adapter = new RecyclerViewAdapter(this, news);

        rvNews.setAdapter(adapter);


    }

    //TODO: Move API credit to app bar
    public void poweredbyNewsAPI(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://newsapi.org/"));
        this.startActivity(intent);
    }

    class getNews extends AsyncTask<Void, Void, List<News>> {

        ProgressBar progressBar = findViewById(R.id.pbGetNewsProgress);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<News> doInBackground(Void... voids) {
            List<News> latestNews = new ArrayList<>();
            try {
                InputStream is = new URL("https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=49d0e3d467744d8d8185bf84162ec9d6").openStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                int ch;
                while ((ch = br.read()) != -1) {
                    sb.append((char) ch);
                }
                is.close();
                JSONObject object = new JSONObject(sb.toString());
                JSONArray articles = object.getJSONArray("articles");
                for (int i = 0, size = articles.length(); i < size; i++) {
                    JSONObject obj = articles.getJSONObject(i);
                    latestNews.add(new News(obj.getString("author"), obj.getString("title"), obj.getString("description"), obj.getString("url"), obj.getString("urlToImage"), obj.getString("publishedAt")));
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
            return latestNews;
        }

        @Override
        protected void onPostExecute(List<News> latestNews) {
            super.onPostExecute(latestNews);
            news.clear();
            news.addAll(latestNews);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }
    }


}
