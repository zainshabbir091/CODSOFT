package com.example.todolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.todolist.Database.Database;
import com.example.todolist.Model.TodoModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Calendar;
import java.util.Locale;

public class AddNewTask extends BottomSheetDialogFragment {



    public static final String TAG = "AddNewTask";

    private EditText Newtask;
    private EditText Newtaskdetail;
    private EditText Newtaskdate;
    private EditText Newtasktime;
    private Button   SaveBtn;
    private Database myDB;
    Calendar mycalendar;
    Calendar mytime;

    public static AddNewTask newInstance(){

        return new AddNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.addtask_activity, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Newtask = view.findViewById(R.id.Newtasktitle);
        Newtaskdetail = view.findViewById(R.id.Newtaskdetail);
        Newtaskdate = view.findViewById(R.id.Newtaskdate);
        Newtasktime = view.findViewById(R.id.Newtasktime);
        SaveBtn = view.findViewById(R.id.savebtn);
        myDB=new Database(getActivity());
        mycalendar=Calendar.getInstance();
        mytime= Calendar.getInstance();


        TextWatcher textWatcher2=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence c, int i, int i1, int i2) {
                String tasktitlecheck=Newtask.getText().toString().trim();
                String tasktdetailcheck=Newtaskdetail.getText().toString().trim();
                String taskdatecheck=Newtaskdate.getText().toString().trim();
                String tasktimecheck=Newtasktime.getText().toString().trim();


                SaveBtn.setEnabled(!tasktitlecheck.isEmpty() && !tasktdetailcheck.isEmpty() && !taskdatecheck.isEmpty() && !tasktimecheck.isEmpty());
               if(SaveBtn.isEnabled())
               {
                   SaveBtn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.blue));
                   SaveBtn.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.white));
               }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        boolean isUpdate= false;

        Bundle bundle = getArguments();
        if(bundle != null)
        {
            isUpdate=true;
            String tasktitle= bundle.getString("tasktitle");
            String taskdetail=bundle.getString("taskdetail");
            String taskdate=bundle.getString("taskdate");
            String tasktime=bundle.getString("tasktime");

            if(tasktitle.length()>0)
            {
                SaveBtn.setEnabled(false);
                Newtask.setText(tasktitle);
                Newtaskdetail.setText(taskdetail);
                Newtasktime.setText(tasktime);
                Newtaskdate.setText(taskdate);
            }
            TextWatcher textWatcher=new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence c, int i, int i1, int i2) {

                    if (c.toString().equals("")){
                        SaveBtn.setEnabled(false);
                        SaveBtn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.grey));
                        SaveBtn.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.white));
                    }else{
                        SaveBtn.setEnabled(true);
                        SaveBtn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.blue));
                        SaveBtn.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.white));
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            };

            Newtask.addTextChangedListener(textWatcher);
            Newtaskdetail.addTextChangedListener(textWatcher);
            Newtasktime.addTextChangedListener(textWatcher);
            Newtaskdate.addTextChangedListener(textWatcher);

        }
        Newtask.addTextChangedListener(textWatcher2);
        Newtaskdetail.addTextChangedListener(textWatcher2);
        Newtasktime.addTextChangedListener(textWatcher2);
        Newtaskdate.addTextChangedListener(textWatcher2);
        boolean finalIsUpdate = isUpdate;
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = Newtask.getText().toString();
                String textdetail = Newtaskdetail.getText().toString();
                String textdate = Newtaskdate.getText().toString();
                String texttime = Newtasktime.getText().toString();

                if (finalIsUpdate){
                    myDB.updateTask(bundle.getInt("id") , text);
                    myDB.updateTaskDetail(bundle.getInt("id"),textdetail );
                    myDB.updateTaskDate(bundle.getInt("id"), textdate );
                    myDB.updateTaskTime(bundle.getInt("id"), texttime );

                }else{
                    TodoModel item = new TodoModel();
                    item.setTask(text);
                    item.setTaskdetail(textdetail);
                    item.setTaskdate(textdate);
                    item.setTasktime(texttime);
                    item.setStatus(0);
                    myDB.insertData(item);
                 }
                dismiss();

            }
        });

        DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                mycalendar.set(Calendar.YEAR,year);
                mycalendar.set(Calendar.MONTH,month);
                mycalendar.set(Calendar.DAY_OF_MONTH,day);
                updateDate();

            }

        };

        Newtaskdate.setOnClickListener(view1 -> {
            Newtaskdate.requestFocus();
            new DatePickerDialog(getActivity(),R.style.DatePickerDialogTheme,date,mycalendar.get(Calendar.YEAR),mycalendar.get(Calendar.MONTH),mycalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        Newtasktime.setOnClickListener(view1 -> {
            selectTime();
        });


    }
    private void selectTime(){
        TimePickerDialog timePickerDialog= new TimePickerDialog(getActivity(),R.style.DatePickerDialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                mytime.set(Calendar.HOUR_OF_DAY,hour);
                mytime.set(Calendar.MINUTE,min);
                String myformat="hh:mm a";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myformat,Locale.US);
                Newtasktime.setText(dateFormat.format(mytime.getTime()));
            }
        },mytime.get(Calendar.HOUR_OF_DAY),mytime.get(Calendar.MINUTE),false) ;

        timePickerDialog.show();
    }

    private void updateDate() {
        String format="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(format, Locale.US);
        Newtaskdate.setText(dateFormat.format(mycalendar.getTime()));
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity= getActivity();
        if (activity instanceof OnDialogCloseListner)
        {
            ((OnDialogCloseListner)activity).onDialogClose(dialog);
        }

    }
}
