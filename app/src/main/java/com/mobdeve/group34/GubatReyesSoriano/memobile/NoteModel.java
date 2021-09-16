package com.mobdeve.group34.GubatReyesSoriano.memobile;

public class NoteModel {
    String id;
    String note_data;
    String created_at;
    String imgUri;



    public NoteModel(String id, String note_data, String created_at) {
        this.id = id;
        this.note_data = note_data;
        this.created_at = created_at;
        this.imgUri = "none";
    }

    public NoteModel(String id, String note_data, String created_at, String imgUri) {
        this.id = id;
        this.note_data = note_data;
        this.created_at = created_at;
        this.imgUri = imgUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote_data() {
        return note_data;
    }

    public void setNote_data(String note_data) {
        this.note_data = note_data;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }
}
