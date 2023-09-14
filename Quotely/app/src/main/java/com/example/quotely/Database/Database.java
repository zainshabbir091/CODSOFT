package com.example.quotely.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quotely.Model.ModelClass;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String DATABASE_NAME="QUOTES_DATABASE";
    private static final String TABLE_NAME="QUOTES_TABLE";
    private static final String COL_1="ID";
    private static final String COL_2="QUOTE";
    private static final String COL_3="AUTHOR";
    private static final String COL_4="FAVORITE";



    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, QUOTE TEXT , AUTHOR TEXT , FAVORITE TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String quote, String author){
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_2, quote);
        values.put(COL_3, author);
        values.put(COL_4, "true");
        db.insert(TABLE_NAME,null,values);
        db.close();
        Log.d("Database Data", quote);
    }

    public List<ModelClass> getData() {
        List<ModelClass> datalist = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                ModelClass task = new ModelClass();
                task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_1)));
                task.setQuote(cursor.getString(cursor.getColumnIndexOrThrow(COL_2)));
                task.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(COL_3)));
                task.setFavorite(cursor.getString(cursor.getColumnIndexOrThrow(COL_4)));
                datalist.add(task);
            }
            cursor.close();
        }

        db.close();

        return datalist;
    }
    public int getRowCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM QUOTES_TABLE", null);
        int count = 0;

        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        }

        db.close();
        return count;
    }
    public void deleteAllData() {
         db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
    public void deleteQuoteById(int id) {
         db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteQuoteByQuote(String quote) {
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_2 + "=?", new String[]{quote});
        db.close();
    }


}
