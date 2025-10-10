package com.mobile.vedroid.java.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.vedroid.java.R;
import com.mobile.vedroid.java.adapter.MessagesAdapter;
import com.mobile.vedroid.java.databinding.FragmentFinalBinding;

import java.util.ArrayList;

public class FinalFragment
        extends DebuggingFragment {

    private FragmentFinalBinding fragmentBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        debugging("HI");
        this.fragmentBinding = FragmentFinalBinding.inflate(inflater, container, false);
        binding = this.fragmentBinding;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MessagesAdapter adapter = new MessagesAdapter(createMockData());

        RecyclerView recyclerView = fragmentBinding.messagesRecyclerView;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        TextView placeholder = fragmentBinding.messagesPlaceholder;
        if (adapter.getItemCount() > 0){
            placeholder.setVisibility(View.GONE);
        } else {
            placeholder.setVisibility(View.VISIBLE);
        }
    }

    private ArrayList<String> createMockData() {
        ArrayList<String> messages = new ArrayList<>();
        String lorem = getString(R.string.lorem_ipsum);
        String text;

        while (lorem.indexOf('.') != -1) {
            text = lorem.substring(0, lorem.indexOf('.'));
            lorem = lorem.substring(lorem.indexOf('.') + 1);
            messages.add(text);
        }

        messages.addAll(messages);
        messages.addAll(messages);

        debugging("create " + (messages).size() + " items");
        return messages;
    }

}
