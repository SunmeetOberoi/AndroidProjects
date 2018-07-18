package com.learnandroid.tvseriestracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.learnandroid.tvseriestracker.R;
import com.learnandroid.tvseriestracker.database.DataBaseDAO;
import com.learnandroid.tvseriestracker.model.Series;

public class Add_EditActivity extends AppCompatActivity {

    int menu_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_activity);

        final DataBaseDAO dao = new DataBaseDAO(this);
        dao.open();
        menu_type=R.menu.menu_edit_plain;

        //Bind Views
        final EditText etTitle = (EditText) findViewById(R.id.etTitle);
        final NumberPicker npSeason = (NumberPicker) findViewById(R.id.npSeason);
        final NumberPicker npEpisode = (NumberPicker) findViewById(R.id.npEpisodes);
        Button btnAdd_Save = (Button) findViewById(R.id.btnAdd_Save);
        setSupportActionBar((Toolbar)findViewById(R.id.add_edit_toolbar));
        boolean newSeries = true;

        //Set the Pickers
        npSeason.setMinValue(1);
        npSeason.setMaxValue(100);
        npEpisode.setMinValue(0);
        npEpisode.setMaxValue(100);

        //Check if called as edit activity
        Intent intent = getIntent();
        String key = "";
        if(intent.getExtras() != null){
            Bundle b = new Bundle();
            b = intent.getExtras();
            etTitle.setText(b.getString("title"));
            npSeason.setValue(b.getInt("season"));
            npEpisode.setValue(b.getInt("episode"));
            btnAdd_Save.setText("Save");
            newSeries=false;
            key = b.getString("title");
            menu_type = R.menu.menu_edit_delete;
        }

        final boolean finalNewSeries = newSeries;
        final String finalKey = key;
        btnAdd_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Series series = new Series(etTitle.getText().toString(), npSeason.getValue(), npEpisode.getValue());
                //if new series
                if(finalNewSeries)
                    dao.add_series(series);
                //if edit already existing series
                else
                    dao.updateSeries(finalKey, series);
                startActivity(new Intent(Add_EditActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menu_type, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_delete){
            //delete
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
