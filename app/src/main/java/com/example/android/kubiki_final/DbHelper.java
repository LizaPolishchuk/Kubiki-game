package com.example.android.kubiki_final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String DB_NAME = "mDatabase";
    public static final String TABLE_NAME = "table_results";
    public static final String COLUMN_ID = "id ";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COUNT = "count";
    //private static final String COLUMN_TIME = "time";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_COUNT + " INTEGER" + ");";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    public long putResult(String name, int count) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_COUNT, count);

        long id = database.insert(TABLE_NAME, null, contentValues);
        database.close();

        return id;
    }

    public Collection<Result> getAllRResults() {
        String name;
        int count;
        int id = 0;

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, COLUMN_COUNT + " DESC");

        ArrayList<Result> results = new ArrayList<>();

        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (cursor.moveToFirst()) {
            do {
                id++;
                name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                count = cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT));
                results.add(new Result(id, name, count));
            } while (cursor.moveToNext());
        }
        return results;
    }

    public Result getResult() {

        String name;
        Integer count;
        Integer id = 1;

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
        count = cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT));
        Log.d("myLogs", "name = " + name + " count = " + String.valueOf(count));

        Result result = new Result(id, name, count);

        cursor.close();
        database.close();

        return result;
    }

    public void cleanDatabase() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, null, null);
    }
}
