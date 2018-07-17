package com.learnandroid.tvseriestracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tvSeries.db";

    public static final String TABLE_NAME = "serials";
    public static final String _ID = "id";
    public static final String COLUMN_TITLE = "name";
    public static final String COLUMN_SEASON = "season";
    public static final String COLUMN_EPISODES = "episodes";

    private static final int DATABASE_VERSION =1;


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + "TEXT NOT NULL, " +
                COLUMN_SEASON + "INTEGER, " +
                COLUMN_EPISODES + "INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
