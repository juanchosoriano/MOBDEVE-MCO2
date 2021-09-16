package com.mobdeve.group34.GubatReyesSoriano.memobile;

public class TodoModel {
    private String id;
    private String todo_text;
    private boolean checked;
    private int priority;

    public TodoModel(String id, String text) {
        this.id = id;
        this.todo_text = text;
        this.checked = false;
        this.priority = 3;
    }

    public TodoModel(String id, String text, boolean checked) {
        this.id = id;
        this.todo_text = text;
        this.checked = checked;
        this.priority = 3;
    }

    public TodoModel(String id, String text, boolean checked, int priority) {
        this.id = id;
        this.todo_text = text;
        this.checked = checked;
        this.priority = priority;
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


}
