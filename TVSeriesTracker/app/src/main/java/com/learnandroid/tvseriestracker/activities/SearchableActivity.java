package com.learnandroid.tvseriestracker.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.learnandroid.tvseriestracker.R;
import com.learnandroid.tvseriestracker.adapters.RecyclerViewAdapter;
import com.learnandroid.tvseriestracker.database.DataBaseDAO;
import com.learnandroid.tvseriestracker.model.Series;

import java.util.ArrayList;
import java.util.List;

public class SearchableActivity extends AppCompatActivity {

    List<Series> seriesList = new ArrayList<>();
    RecyclerView rvSearch;
    RecyclerViewAdapter recyclerViewAdapter;
    DataBaseDAO dao = new DataBaseDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        //TODO: add navigate up button

        //Connect to the database
        dao.open();

        //Set the recycler view
        rvSearch = (RecyclerView)findViewById(R.id.rvSearch);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvSearch.setLayoutManager(llm);
        rvSearch.setHasFixedSize(true);
        recyclerViewAdapter = new RecyclerViewAdapter(this, seriesList);
        rvSearch.setAdapter(recyclerViewAdapter);

        //Fetch query
        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            search(query);
        }


    }

    private void search(String query) {
        List<Series> database = dao.getAllSeries();
        seriesList.clear();
        int status;
        for(int i=0;i<database.size();i++) {
            status = 0;
            for (int j = 0; j < database.get(i).getTitle().length() && j < query.length(); j++)
                if (Character.toLowerCase(database.get(i).getTitle().charAt(j)) == Character.toLowerCase(query.charAt(j)))
                    status++;
            if(status == query.length())
                seriesList.add(database.get(i));
        }
        if(seriesList.size() == 0)
            findViewById(R.id.tvNoMatch).setVisibility(View.VISIBLE);
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
