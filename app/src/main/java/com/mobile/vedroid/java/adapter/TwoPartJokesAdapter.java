package com.mobile.vedroid.java.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.vedroid.java.databinding.ItemJokeBinding;
import com.mobile.vedroid.java.model.DenoJoke;

public class TwoPartJokesAdapter
    extends ExpandableAdapter <DenoJoke, TwoPartJokesAdapter.ViewHolder> {

    @NonNull
    @Override
    public TwoPartJokesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemJokeBinding binding = ItemJokeBinding
                .inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TwoPartJokesAdapter.ViewHolder holder, int position) {
        holder.bindItem(jokes.get(position));
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemJokeBinding binding;

        public ViewHolder(ItemJokeBinding binding){
            super(binding.getRoot());
            this.binding = binding;

            binding.itemSingleJokeText.setVisibility(View.GONE);
            binding.itemTwopartJokeSetup.setVisibility(View.VISIBLE);
            binding.itemTwopartJokeDelivery.setVisibility(View.VISIBLE);
        }

        void bindItem (DenoJoke item){
            binding.itemTwopartJokeSetup.setText(item.getSetup());
            binding.itemTwopartJokeDelivery.setText(item.getDelivery());
        }
    }
}