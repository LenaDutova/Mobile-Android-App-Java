package com.mobile.vedroid.java.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.vedroid.java.R;

import java.util.ArrayList;

public class MessagesAdapter
        extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private final ArrayList<String> messages;
    private final Context context;

    public MessagesAdapter(Context context, ArrayList<String> messages) {
        this.context = context;
        this.messages = messages;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView text;

        ViewHolder(View view){
            super(view);

            // Find views on layout
            text = view.findViewById(R.id.item_message_text);
        }
    }

    @NonNull
    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Specify layout for items
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.ViewHolder holder, int position) {
        // Show data from position item on layout views
        holder.text.setText(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}