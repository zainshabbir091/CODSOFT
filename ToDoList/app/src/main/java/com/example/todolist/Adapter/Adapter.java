package com.example.todolist.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.AddNewTask;
import com.example.todolist.Database.Database;
import com.example.todolist.MainActivity;
import com.example.todolist.Model.TodoModel;
import com.example.todolist.R;

import java.util.Collections;
import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    public MainActivity context;
    private List<TodoModel> mylist;
    private MainActivity activity;
    private Database myDB;

    public Adapter(Database myDB, MainActivity activity)
    {
        this.context=activity;
        this.activity=activity;
        this.myDB=myDB;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
       return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TodoModel item= mylist.get(position);
        holder.title.setText(item.getTask());
        holder.detail.setText(item.getTaskdetail());
        holder.date.setText(item.getTaskdate());
        holder.time.setText(item.getTasktime());
        holder.status.setText(getstatus(item.getStatus()));
        //holder.options.setOnClickListener(   view -> showPopUp(view,position));
        holder.options.setOnClickListener(view -> {
            int adapterPosition = holder.getAdapterPosition(); // Get the adapter position
            if (adapterPosition != RecyclerView.NO_POSITION) {
                // Check if the position is valid
                showPopUp(view, adapterPosition); // Pass the adapter position to your method
            }
        });



    }

    public void showPopUp(View v, int position){
        final TodoModel listitem= mylist.get(position);
            PopupMenu popupMenu= new PopupMenu(context,v);
            if(listitem.getStatus()==0)
            {
                popupMenu.inflate(R.menu.menu);
                popupMenu.setOnMenuItemClickListener(item ->{
                    switch (item.getItemId()){
                        case R.id.menuDelete:
                           // Toast.makeText(context,  listitem.getTask(), Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(context);
                            alertDialogBuilder.setTitle("Delete Task");
                            alertDialogBuilder.setMessage("Are You Sure ?");
                            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    deleteTask(position);

                                }
                            });
                            alertDialogBuilder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    notifyItemChanged(position);
                                }
                            });
                            AlertDialog dialog = alertDialogBuilder.create();
                            dialog.show();
                            //Toast.makeText(context,"Delete", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.menuUpdate:
                            editTask(position);
                            //Toast.makeText(context,"Update", Toast.LENGTH_SHORT).show();

                            break;
                        case R.id.menuComplete:
                            AlertDialog.Builder completeDialog= new AlertDialog.Builder(context);
                            completeDialog.setTitle("Sure To Mark Task As Complete");
                            completeDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    myDB.updateStatus(listitem.getId(),1);
                                    List<TodoModel> updatedData = myDB.getData();
                                    updateData(updatedData);
                                    Collections.reverse(mylist);
                                    notifyDataSetChanged();

                                }
                            });
                            completeDialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    notifyItemChanged(position);
                                }
                            });
                            completeDialog.create().show();

                            //  Toast.makeText(context,"Complete", Toast.LENGTH_SHORT).show();

                            break;
                    }
                    return false;
                });
            }
            else
            {
                popupMenu.inflate(R.menu.completed_menu);
                popupMenu.setOnMenuItemClickListener(item ->{
                    switch (item.getItemId()){
                        case R.id.menuDelete:
                            AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(context);
                            alertDialogBuilder.setTitle("Delete Task");
                            alertDialogBuilder.setMessage("Are You Sure ?");
                            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    deleteTask(position);

                                }
                            });
                            alertDialogBuilder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    notifyItemChanged(position);
                                }
                            });
                            AlertDialog dialog = alertDialogBuilder.create();
                            dialog.show();
                            //Toast.makeText(context,"Delete", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.menuUpdate:
                            editTask(position);
                            //Toast.makeText(context,"Update", Toast.LENGTH_SHORT).show();

                            break;
                        case R.id.menuPending:
                            AlertDialog.Builder completeDialog= new AlertDialog.Builder(context);
                            completeDialog.setTitle("Sure To Mark Task As Pending");
                            completeDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    myDB.updateStatus(listitem.getId(),0);
                                    List<TodoModel> updatedData = myDB.getData();
                                    updateData(updatedData);
                                    Collections.reverse(mylist);
                                    notifyDataSetChanged();

                                }
                            });
                            completeDialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    notifyItemChanged(position);
                                }
                            });
                            completeDialog.create().show();

                            //  Toast.makeText(context,"Complete", Toast.LENGTH_SHORT).show();

                            break;
                    }
                    return false;
                });
            }

        popupMenu.show();

    }
    public String getstatus(int num)
    {
        if(num==0)
        {
            return "Pending";
        }
        else {
            return "Completed";
        }
    }

    public Context getContext(){
        return activity;
    }
    public void setTasks(List<TodoModel> mylist)
    {
        this.mylist =mylist;
        notifyDataSetChanged();
    }
    public void deleteTask(int pos)
    {
        TodoModel item = mylist.get(pos);
        myDB.deleteTask(item.getId());
        mylist.remove(pos);
        notifyItemRemoved(pos);

    }

    public void editTask(int pos)
    {
        TodoModel item = mylist.get(pos);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("tasktitle", item.getTask());
        bundle.putString("taskdetail", item.getTaskdetail());
        bundle.putString("taskdate", item.getTaskdate());
        bundle.putString("tasktime", item.getTasktime());

        AddNewTask task = new AddNewTask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager(),task.getTag());

    }

    public void updateData(List<TodoModel> newData) {
        mylist = newData;
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView detail;
        TextView time;
        TextView date;
        TextView status;
        ImageView options;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title =itemView.findViewById(R.id.title);
            detail=itemView.findViewById(R.id.description);
            date=itemView.findViewById(R.id.date);
            time=itemView.findViewById(R.id.time);
            status=itemView.findViewById(R.id.status);
            options=itemView.findViewById(R.id.options);
        }
    }
}
