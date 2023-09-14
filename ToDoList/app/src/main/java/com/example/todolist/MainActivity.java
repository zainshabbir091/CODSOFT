package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.todolist.Adapter.Adapter;
import com.example.todolist.Database.Database;
import com.example.todolist.Model.TodoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListner{


    private RecyclerView RecycleView;
    private FloatingActionButton addBtn;
    private Database myDB;
    private List<TodoModel> mylist;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecycleView =findViewById(R.id.recyclerview);
        addBtn = findViewById(R.id.addtaskbtn);
        myDB = new Database(MainActivity.this);
        mylist= new ArrayList<>();
        adapter = new Adapter(myDB, MainActivity.this);
        RecycleView.setHasFixedSize(true);
        RecycleView.setLayoutManager(new LinearLayoutManager(this));
        RecycleView.setAdapter(adapter);
        mylist=myDB.getData();
        Collections.reverse(mylist);
        adapter.setTasks(mylist);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mylist=myDB.getData();
        Collections.reverse(mylist);
        adapter.setTasks(mylist);
        adapter.notifyDataSetChanged();
    }
}