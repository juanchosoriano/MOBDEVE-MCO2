package com.mobdeve.group34.GubatReyesSoriano.memobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TodoAdaptor extends RecyclerView.Adapter {

    List<TodoModel> todoModelList;
    TodoClickListener todoClickListener;
    //TodoCheckListener todoCheckListener;
    public TodoAdaptor(List<TodoModel> todoModelList, TodoClickListener todoClickListener){
        this.todoModelList = todoModelList;
        this.todoClickListener = todoClickListener;
        //this.todoClickListener
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public CheckBox checkBox;
        public LinearLayout todoLayout;
        ItemClickListener itemClickListener;

        public TodoViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.cb_item);
            todoLayout = itemView.findViewById(R.id.ll_todo);
        }
        public void setItemClickListener(ItemClickListener ic)
        {
            this.itemClickListener=ic;
        }
        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }

        interface ItemClickListener {

            void onItemClick(View v,int pos);
        }
    }
    @NonNull
    @NotNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row, null);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        TodoViewHolder todoViewHolder = (TodoViewHolder) holder;
        todoViewHolder.checkBox.setText(todoModelList.get(position).getTodo_Text());
        todoViewHolder.todoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CheckBox mycheckbox = (CheckBox) v;
                todoClickListener.onClickItem(todoModelList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoModelList.size();
    }
}
