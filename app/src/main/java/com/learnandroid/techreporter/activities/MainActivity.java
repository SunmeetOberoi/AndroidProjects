package com.learnandroid.techreporter.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.learnandroid.techreporter.R;
import com.learnandroid.techreporter.adapters.RecyclerViewAdapter;
import com.learnandroid.techreporter.models.News;

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

        news.add(new News("Catherine Shu","500px nixes Creative Commons option and replaces Marketplace with Getty and Visual China Group partnerships","Photographers who use 500px to distribute their work are now adjusting to two big changes. First, the platform is removing the option to upload or download photos with a Creative Commons license. On Saturday, it also shut down its stock photo platform 500px M…", "https://techcrunch.com/2018/07/01/500px-nixes-creative-commons-option-and-replaces-marketplace-with-getty-and-visual-china-group-partnerships/", "https://techcrunch.com/wp-content/uploads/2016/09/500px.jpg?w=711","2018-07-02T03:19:41Z"));
        news.add(new News("Kirsten Korosec","Tesla hits Model 3 production goal","Tesla appears to have produced nearly 5,000 Model 3 electric vehicles in the last week of June, coming within hours of hitting a target CEO Elon Musk initially planned to meet by the end of 2017. The 5,000th Model 3 came off the assembly line early Sunday mor…", "https://techcrunch.com/2018/07/01/tesla-model-3-production-goal/", "https://techcrunch.com/wp-content/uploads/2018/03/tesla-model-3-7.jpg?w=600","2018-07-01T20:56:12Z"));
    }
}
