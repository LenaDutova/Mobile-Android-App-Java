package com.mobile.vedroid.java.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.vedroid.java.databinding.ItemJokeBinding;
import com.mobile.vedroid.java.model.JokeAdapterModel;

import java.util.ArrayList;
import java.util.List;

public class JokesAdapter
        extends RecyclerView.Adapter<JokesAdapter.ViewHolder> {

    protected List<JokeAdapterModel> jokes = new ArrayList<JokeAdapterModel>();

    public JokesAdapter() { }
    public JokesAdapter(List<JokeAdapterModel> jokes) {
        this.jokes.addAll(jokes);
    }

    public List<JokeAdapterModel> addItems(List<JokeAdapterModel> newJokes) {
        List<JokeAdapterModel> addedJokes = new ArrayList<>();
        for (JokeAdapterModel joke : newJokes) {
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

        void bindItem (JokeAdapterModel item){
            if (item.isSingle()){
                binding.itemSingleJokeText.setVisibility(View.VISIBLE);
                binding.itemTwopartJokeSetup.setVisibility(View.GONE);
                binding.itemTwopartJokeDelivery.setVisibility(View.GONE);

                binding.itemSingleJokeText.setText(item.getSetup());
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
