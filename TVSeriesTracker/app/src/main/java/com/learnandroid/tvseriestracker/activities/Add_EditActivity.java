package com.learnandroid.tvseriestracker.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
    String key = "";
    DataBaseDAO dao;
    boolean newSeries = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_activity);

        dao = new DataBaseDAO(this);
        dao.open();
        //Initialise toolbar to add series type
        menu_type = R.menu.menu_edit_plain;

        //Bind Views
        final EditText etTitle = (EditText) findViewById(R.id.etTitle);
        final NumberPicker npSeason = (NumberPicker) findViewById(R.id.npSeason);
        final NumberPicker npEpisode = (NumberPicker) findViewById(R.id.npEpisodes);
        Button btnAdd_Save = (Button) findViewById(R.id.btnAdd_Save);
        setSupportActionBar((Toolbar) findViewById(R.id.add_edit_toolbar));

        //Set the Pickers
        npSeason.setMinValue(1);
        npSeason.setMaxValue(100);
        npEpisode.setMinValue(0);
        npEpisode.setMaxValue(100);

        //Check if called as edit activity
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle b = new Bundle();
            b = intent.getExtras();
            //Set the views to display the series content which is being edited
            etTitle.setText(b.getString("title"));
            npSeason.setValue(b.getInt("season"));
            npEpisode.setValue(b.getInt("episode"));
            btnAdd_Save.setText("Save");
            newSeries = false;
            key = b.getString("title");
            menu_type = R.menu.menu_edit_delete;
        }

        btnAdd_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Series series = new Series(etTitle.getText().toString(), npSeason.getValue(), npEpisode.getValue());
                //if new series
                if (newSeries)
                    dao.add_series(series);
                    //if editing already existing series
                else
                    dao.updateSeries(key, series);
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

        if (id == R.id.action_delete) {
            LayoutInflater layoutInflater = LayoutInflater.from(Add_EditActivity.this);
            View dialogView = layoutInflater.inflate(R.layout.dialog_confirm_delete, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(Add_EditActivity.this);
            builder.setView(dialogView);
            builder
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.confirm_delete_positive), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dao.deleteSeries(key);
                            startActivity(new Intent(Add_EditActivity.this, MainActivity.class));
                        }
                    })
                    .setNegativeButton(getString(R.string.confirm_delete_negative), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create()
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
