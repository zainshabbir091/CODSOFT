package com.example.todolist.Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class TodoModel {

    private String task,taskdetail,tasktime,taskdate;
    private int id,status;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTaskdetail() {
        return taskdetail;
    }

    public void setTaskdetail(String taskdetail) {
        this.taskdetail = taskdetail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getTasktime() {
        return tasktime;
    }

    public void setTasktime(String tasktime) {
        this.tasktime = tasktime;
    }

    public String getTaskdate() {
        return taskdate;
    }

    public void setTaskdate(String taskdate) {
        this.taskdate = taskdate;
    }
}
