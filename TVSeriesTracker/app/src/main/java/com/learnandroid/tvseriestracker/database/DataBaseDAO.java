package com.learnandroid.tvseriestracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.learnandroid.tvseriestracker.model.Series;

import java.util.ArrayList;

import static com.learnandroid.tvseriestracker.database.DataBaseHelper.COLUMN_EPISODES;
import static com.learnandroid.tvseriestracker.database.DataBaseHelper.COLUMN_SEASON;
import static com.learnandroid.tvseriestracker.database.DataBaseHelper.COLUMN_TITLE;
import static com.learnandroid.tvseriestracker.database.DataBaseHelper.TABLE_NAME;


public class DataBaseDAO {
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase database;

    public DataBaseDAO(Context context) {
        this.dataBaseHelper = new DataBaseHelper(context);
    }

    public void open(){
        database = dataBaseHelper.getReadableDatabase();
    }

    public void close(){
        dataBaseHelper.close();
    }

    public void add_series(Series series){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, series.getTitle());
        values.put(COLUMN_SEASON, series.getSeason());
        values.put(COLUMN_EPISODES, series.getEpisode());
        long id = database.insert(TABLE_NAME, null, values);
        if(id < 0)
            Log.e("DAO", "Unable to perform insert operation");
    }

    public ArrayList<Series> getAllSeries(){
        ArrayList<Series> serials = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            serials.add(new Series(
                    cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_SEASON)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_EPISODES))
            ));
            cursor.moveToNext();
        }
        cursor.close();
        return serials;
    }
}
