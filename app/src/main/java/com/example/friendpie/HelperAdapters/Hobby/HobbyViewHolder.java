package com.example.friendpie.HelperAdapters.Hobby;

import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendpie.R;

public class HobbyViewHolder extends RecyclerView.ViewHolder {

    CheckBox hobby_checkbox;

    public HobbyViewHolder(@NonNull View itemView) {
        super(itemView);

        hobby_checkbox = itemView.findViewById(R.id.hobby_checkbox);

    }

    public CheckBox getHobby_checkbox() {
        return hobby_checkbox;
    }


}
