package com.mobdeve.group34.GubatReyesSoriano.memobile;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NoteAdaptor extends RecyclerView.Adapter {

    List<NoteModel> noteModelList;
    NoteClickListener noteClickListener;

    public NoteAdaptor(List<NoteModel> noteModels, NoteClickListener noteClickListener){
        this.noteModelList = noteModels;
        this.noteClickListener = noteClickListener;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        public TextView noteText, create_time;
        public LinearLayout noteLayout;

        public NoteViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            noteText = itemView.findViewById(R.id.tv_note_data);
            create_time = itemView.findViewById(R.id.tv_note_date);
            noteLayout = itemView.findViewById(R.id.ll_note);
        }
    }
    @NonNull
    @NotNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_row, null);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        NoteViewHolder noteViewHolder = (NoteViewHolder) holder;
        //Log.d("NOTE_TEXT", noteModelList.get(position).getNote_data());
        noteViewHolder.noteText.setText(noteModelList.get(position).getNote_data());
        String format_date = noteModelList.get(position).getCreated_at();
        try {
            Date created_date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(noteModelList.get(position).getCreated_at());
            format_date = new SimpleDateFormat("MMM dd yyyy").format(created_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        noteViewHolder.create_time.setText(format_date);
        noteViewHolder.noteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteClickListener.onClickItem(noteModelList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteModelList.size();
    }
}
