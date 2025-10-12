package com.mobile.vedroid.java.adapter;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public abstract class ExpandableAdapter<T, K extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<K> {

    protected ArrayList<T> jokes = new ArrayList<T>();

    public ExpandableAdapter() { }
    public ExpandableAdapter(ArrayList<T> jokes) {
        this.jokes.addAll(jokes);
    }

    @Override
    public int getItemCount() {
        return jokes.size();
    }

    public int addItems(ArrayList<T> newJokes) {
        int count = 0;
        for (T joke : newJokes) {
            if (!jokes.contains(joke)) {
                jokes.add(0, joke);
                count++;
            }
        }
        if (count > 0) this.notifyDataSetChanged();
        return count;
    }
}
