package com.example.friendpie.UserHomeScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.friendpie.Interfaces.MainCallback;
import com.example.friendpie.R;

public class MessageFragment extends Fragment {

    private MainCallback callback = null;
    private View view;

    public MessageFragment() {
        // Required empty public constructor
    }

    public void setCallback(MainCallback callback){
        this.callback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message, container, false);
        return view;
    }
}