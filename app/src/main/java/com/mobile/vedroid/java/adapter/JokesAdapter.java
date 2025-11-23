package com.mobile.vedroid.java.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.vedroid.java.databinding.ItemJokeBinding;
import com.mobile.vedroid.java.model.JokeModelAdapter;

import java.util.ArrayList;

public class JokesAdapter
        extends RecyclerView.Adapter<JokesAdapter.ViewHolder> {

    protected ArrayList<JokeModelAdapter> jokes = new ArrayList<JokeModelAdapter>();

    public JokesAdapter() { }
    public JokesAdapter(ArrayList<JokeModelAdapter> jokes) {
        this.jokes.addAll(jokes);
    }

    public ArrayList<JokeModelAdapter> addItems(ArrayList<JokeModelAdapter> newJokes) {
        ArrayList<JokeModelAdapter> addedJokes = new ArrayList<>();
        for (JokeModelAdapter joke : newJokes) {
            if (!jokes.contains(joke)) {
                jokes.add(0, joke);
                addedJokes.add(joke);
            }
        }
        if (!addedJokes.isEmpty()) this.notifyDataSetChanged();
        return addedJokes;
    }

    @Override
    public int getItemCount() {
        return jokes.size();
    }

    @NonNull
    @Override
    public JokesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemJokeBinding binding = ItemJokeBinding
                .inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false);
        return new JokesAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull JokesAdapter.ViewHolder holder, int position) {
        holder.bindItem(jokes.get(position));
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemJokeBinding binding;

        public ViewHolder(ItemJokeBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindItem (JokeModelAdapter item){
            if (item.isSingleJoke()){
                binding.itemSingleJokeText.setVisibility(View.VISIBLE);
                binding.itemTwopartJokeSetup.setVisibility(View.GONE);
                binding.itemTwopartJokeDelivery.setVisibility(View.GONE);

                binding.itemSingleJokeText.setText(item.getJokeSetup());
            }
            else {
                binding.itemSingleJokeText.setVisibility(View.GONE);
                binding.itemTwopartJokeSetup.setVisibility(View.VISIBLE);
                binding.itemTwopartJokeDelivery.setVisibility(View.VISIBLE);

                binding.itemTwopartJokeSetup.setText(item.getJokeSetup());
                binding.itemTwopartJokeDelivery.setText(item.getJokeDelivery());
            }
        }
    }
}
