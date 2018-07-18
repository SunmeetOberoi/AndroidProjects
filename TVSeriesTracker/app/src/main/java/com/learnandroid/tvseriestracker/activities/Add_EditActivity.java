package com.learnandroid.tvseriestracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.learnandroid.tvseriestracker.R;
import com.learnandroid.tvseriestracker.database.DataBaseDAO;
import com.learnandroid.tvseriestracker.model.Series;

public class Add_EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__edit);

        final DataBaseDAO dao = new DataBaseDAO(this);
        dao.open();

        //Bind Views
        final EditText etTitle = (EditText) findViewById(R.id.etTitle);
        final NumberPicker npSeason = (NumberPicker) findViewById(R.id.npSeason);
        final NumberPicker npEpisode = (NumberPicker) findViewById(R.id.npEpisodes);
        Button btnAdd = (Button) findViewById(R.id.btnAdd);

        //Set the Pickers
        npSeason.setMinValue(1);
        npSeason.setMaxValue(100);
        npEpisode.setMinValue(0);
        npEpisode.setMaxValue(100);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Series series = new Series(etTitle.getText().toString(), npSeason.getValue(), npEpisode.getValue());
                dao.add_series(series);
                startActivity(new Intent(Add_EditActivity.this, MainActivity.class));
            }
        });
    }
}
