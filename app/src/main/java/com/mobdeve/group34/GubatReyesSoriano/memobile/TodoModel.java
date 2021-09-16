package com.mobdeve.group34.GubatReyesSoriano.memobile;

public class TodoModel {
    private String id;
    private String todo_text;
    private boolean checked;

    public TodoModel(String id, String text) {
        this.id = id;
        this.todo_text = text;
        this.checked = false;
    }

    public TodoModel(String id, String text, boolean checked) {
        this.id = id;
        this.todo_text = text;
        this.checked = checked;
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
