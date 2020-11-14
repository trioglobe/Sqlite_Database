package com.ams.sqlite_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Button;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class sqlite extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db";
    private static final String TABLE = "table_";
    private static final String KEY_ID = "id";
    private static final String KEY_VALUE = "value";

    public sqlite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_VALUE + " TEXT )";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public Boolean add(String value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_VALUE, value);
        long result = db.insert(TABLE, null, values);
        db.close();
        if(result==-1)
            return false;
        else
            return true;
    }

    public Boolean update(int id, String value){
        String query = "UPDATE " + TABLE + " SET " + KEY_VALUE + " = "+ " ' " + value + " ' " +" WHERE " + KEY_ID + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        return null;
    }

    public List<dbHelper> getAllData(){
        List<dbHelper> List = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                dbHelper dbHelper = new dbHelper(cursor.getInt(0), cursor.getString(1));
                List.add(dbHelper);
            } while (cursor.moveToNext());
        }
        db.close();
        return List;
    }

    public Boolean delete(int id){
        String query = "DELETE FROM " + TABLE + " WHERE " + KEY_ID + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        return null;
    }
}
