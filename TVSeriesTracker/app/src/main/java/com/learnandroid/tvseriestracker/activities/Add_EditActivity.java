package com.learnandroid.tvseriestracker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.learnandroid.tvseriestracker.R;

public class Add_EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__edit);
        EditText etTitle = (EditText) findViewById(R.id.etTitle);
        NumberPicker npSeason = (NumberPicker) findViewById(R.id.npSeason);
        NumberPicker npEpisode = (NumberPicker) findViewById(R.id.npEpisodes);
        npSeason.setMinValue(1);
        npSeason.setMaxValue(100);
        npEpisode.setMinValue(0);
        npEpisode.setMaxValue(100);
    }
}
