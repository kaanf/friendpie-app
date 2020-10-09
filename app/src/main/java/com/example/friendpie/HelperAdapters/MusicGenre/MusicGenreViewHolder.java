package com.example.friendpie.HelperAdapters.MusicGenre;

import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendpie.R;

public class MusicGenreViewHolder extends RecyclerView.ViewHolder {

    CheckBox musicGenre_checkbox;

    public MusicGenreViewHolder(@NonNull View itemView) {
        super(itemView);

        musicGenre_checkbox = itemView.findViewById(R.id.music_checkbox);

    }

    public CheckBox getMusicGenre_checkbox() {
        return musicGenre_checkbox;
    }

}
