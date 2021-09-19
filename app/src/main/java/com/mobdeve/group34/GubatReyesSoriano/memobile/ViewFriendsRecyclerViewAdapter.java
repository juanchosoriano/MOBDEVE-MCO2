package com.mobdeve.group34.GubatReyesSoriano.memobile;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewFriendsRecyclerViewAdapter extends RecyclerView.Adapter<ViewFriendsRecyclerViewAdapter.ViewHolder> {
    private List<String> friendsList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(ViewGroup viewGroup) {
            super(viewGroup);
            mTextView = viewGroup.findViewById(R.id.tv_friendname);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ViewFriendsRecyclerViewAdapter(List<String> friendsList) {
        this.friendsList = friendsList;
        Log.d("Emails", "" + this.friendsList.size());
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewFriendsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friends_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(friendsList.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return friendsList.size();
    }
}

