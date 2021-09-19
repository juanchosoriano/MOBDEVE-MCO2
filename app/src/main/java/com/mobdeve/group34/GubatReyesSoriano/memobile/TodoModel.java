package com.mobdeve.group34.GubatReyesSoriano.memobile;

import java.util.Date;

public class TodoModel {
    private String id;
    private String todo_text;
    private boolean checked;
    private int priority;
    private Date todo_date;

    public TodoModel(String id, String text) {
        this.id = id;
        this.todo_text = text;
        this.checked = false;
        this.priority = 3;
        this.todo_date = new Date();
    }

    public TodoModel(String id, String text, boolean checked) {
        this.id = id;
        this.todo_text = text;
        this.checked = checked;
        this.priority = 3;
        this.todo_date = new Date();
    }

    public TodoModel(String id, String text, boolean checked, int priority) {
        this.id = id;
        this.todo_text = text;
        this.checked = checked;
        this.priority = priority;
        this.todo_date = new Date();
    }

    public TodoModel(String id, String text, boolean checked, int priority, Date date) {
        this.id = id;
        this.todo_text = text;
        this.checked = checked;
        this.priority = priority;
        this.todo_date = date;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTodo_Text() {
        return todo_text;
    }

    public void setTodo_Text(String text) {
        this.todo_text = text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Date getTodo_date() {
        return todo_date;
    }

    public void setTodo_date(Date todo_date) {
        this.todo_date = todo_date;
    }
}
