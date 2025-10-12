package com.mobile.vedroid.java.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.vedroid.java.databinding.ItemJokeBinding;
import com.mobile.vedroid.java.model.ApiJoke;

public class MixedJokesAdapter
        extends ExpandableAdapter <ApiJoke, MixedJokesAdapter.ViewHolder> {

    @NonNull
    @Override
    public MixedJokesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemJokeBinding binding = ItemJokeBinding
                .inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false);
        return new MixedJokesAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MixedJokesAdapter.ViewHolder holder, int position) {
        holder.bindItem(jokes.get(position));
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemJokeBinding binding;

        public ViewHolder(ItemJokeBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindItem (ApiJoke item){
            if (item.isTypeSingle()){
                binding.itemSingleJokeText.setVisibility(View.VISIBLE);
                binding.itemTwopartJokeSetup.setVisibility(View.GONE);
                binding.itemTwopartJokeDelivery.setVisibility(View.GONE);

                binding.itemSingleJokeText.setText(item.getJoke());
            }
            else {
                binding.itemSingleJokeText.setVisibility(View.GONE);
                binding.itemTwopartJokeSetup.setVisibility(View.VISIBLE);
                binding.itemTwopartJokeDelivery.setVisibility(View.VISIBLE);

                binding.itemTwopartJokeSetup.setText(item.getSetup());
                binding.itemTwopartJokeDelivery.setText(item.getDelivery());
            }
        }
    }
}
