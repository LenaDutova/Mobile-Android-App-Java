package com.mobile.vedroid.java.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.vedroid.java.databinding.ItemMessageBinding;

import java.util.ArrayList;

public class MessagesAdapter
        extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private final ArrayList<String> messages;

    public MessagesAdapter(ArrayList<String> messages) {
        this.messages = messages;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemMessageBinding binding;

        public ViewHolder(ItemMessageBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindItem (String item){
            binding.itemMessageText.setText (item);
        }
    }

    @NonNull
    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMessageBinding binding = ItemMessageBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.ViewHolder holder, int position) {
        holder.bindItem(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}