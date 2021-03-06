package com.learnandroid.tvseriestracker.activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.learnandroid.tvseriestracker.R;
import com.learnandroid.tvseriestracker.adapters.RecyclerViewAdapter;
import com.learnandroid.tvseriestracker.database.DataBaseDAO;
import com.learnandroid.tvseriestracker.helper.SimpleItemTouchHelper;
import com.learnandroid.tvseriestracker.model.Series;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Series> seriesList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    DataBaseDAO dao;
    FloatingActionButton fabSaveToDatabase;
    SimpleItemTouchHelper simpleItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fabSaveToDatabase = (FloatingActionButton) findViewById(R.id.fabSaveToDatabase);


        //Create DataBase object
        dao = new DataBaseDAO(this);
        dao.open();

        //Populate the list with already stored Dataset
        seriesList = dao.getAllSeries();


        //set Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.rvTVSeries);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerViewAdapter = new RecyclerViewAdapter(this, seriesList);
        recyclerView.setAdapter(recyclerViewAdapter);
        simpleItemTouchHelper = new SimpleItemTouchHelper(recyclerViewAdapter);


        fabSaveToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.updateDatabase(recyclerViewAdapter.seriesList);
                fabSaveToDatabase.setVisibility(View.GONE);
                simpleItemTouchHelper.dragState = false;
            }
        });

        //TODO: Display a message if no series are currently in the database
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Refreshes when activity resumes
        seriesList.clear();
        seriesList.addAll(dao.getAllSeries());
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo( searchManager.getSearchableInfo(new
                ComponentName(this,SearchableActivity.class)));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            startActivity(new Intent(this, Add_EditActivity.class));
            return true;
        }
        if(id == R.id.edit){
            fabSaveToDatabase.setVisibility(View.VISIBLE);
            ItemTouchHelper.Callback callback = simpleItemTouchHelper;
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(recyclerView);
            simpleItemTouchHelper.dragState = true;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
