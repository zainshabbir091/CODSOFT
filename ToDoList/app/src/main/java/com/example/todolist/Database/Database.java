package com.example.todolist.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todolist.Model.TodoModel;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String DATABASE_NAME="TODOLIST_DATABASE";
    private static final String TABLE_NAME="TODOLIST_TABLE";
    private static final String COL_1="ID";
    private static final String COL_2="TASK";
    private static final String COL_3="TASKDETAIL";
    private static final String COL_4="DATE";
    private static final String COL_5="TIME";
    private static final String COL_6="STATUS";



    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT , TASKDETAIL TEXT , DATE TEXT , TIME TEXT, STATUS INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }

    public void insertData(TodoModel model)
    {
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_2, model.getTask());
        values.put(COL_3, model.getTaskdetail());
        values.put(COL_4, model.getTaskdate());
        values.put(COL_5, model.getTasktime());
        values.put(COL_6, 0);
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public void updateTask(int id, String task)
    {
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_2 , task);
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});

    }
    public void updateTaskDetail(int id, String taskDetail)
    {
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_3, taskDetail);;
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});

    }
    public void updateTaskDate(int id,String taskDate)
    {
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_4, taskDate);
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});

    }
    public void updateTaskTime(int id, String taskTime)
    {
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_5, taskTime);
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});

    }
    public void updateStatus(int id, int status)
    {
        db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_6 , status);
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});

    }

    public void deleteTask(int id)
    {
        db=this.getWritableDatabase();
db.delete(TABLE_NAME,"ID=?", new String[]{String.valueOf(id)});
    }
    public List<TodoModel> getData() {
         db = this.getWritableDatabase();
        List<TodoModel> modelList = new ArrayList<>();

        if (db == null) {
            // Handle the case where the database instance is null
            return modelList; // Return an empty list or handle the situation accordingly
        }

        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    TodoModel task = new TodoModel();
                    task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_1)));
                    task.setTask(cursor.getString(cursor.getColumnIndexOrThrow(COL_2)));
                    task.setTaskdetail(cursor.getString(cursor.getColumnIndexOrThrow(COL_3)));
                    task.setTaskdate(cursor.getString(cursor.getColumnIndexOrThrow(COL_4)));
                    task.setTasktime(cursor.getString(cursor.getColumnIndexOrThrow(COL_5)));
                    task.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(COL_6)));

                    modelList.add(task);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close(); // Close the database when done with the cursor
        }

        return modelList;
    }

//    public List<TodoModel> getData(){
//
//        db=this.getWritableDatabase();
//       // Cursor cursor= null;
//        Cursor cursor= db.query(TABLE_NAME, null, null, null, null, null, null);
//
//        List<TodoModel> modelList=new ArrayList<>();
//        db.beginTransaction();
//        try{
//            if(cursor !=null)
//            {
//                if(cursor.moveToFirst()){
//                    do{
//                        TodoModel task = new TodoModel();
//                        task.setId(cursor.getInt(0));
//                        task.setTask(cursor.getString(1));
//                        task.setTaskdetail(cursor.getString(2));
//                        task.setTaskdate(cursor.getString(3));
//                        task.setTasktime(cursor.getString(4));
//                        task.setStatus(cursor.getInt(5));
//                        modelList.add(task);
//
//                    }while(cursor.moveToNext());
//                }
//            }
//
//        }finally {
//            db.endTransaction();
//            cursor.close();
//        }
//        return modelList;
//    }

}
