package com.example.friendpie.HelperAdapters.ProfileMusicGenres;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendpie.R;

public class ProfileMusicGenreViewHolder extends RecyclerView.ViewHolder {

    TextView music_profile_title;

    public ProfileMusicGenreViewHolder(@NonNull View itemView) {
        super(itemView);
        music_profile_title = itemView.findViewById(R.id.music_profile_title);
    }

    public TextView getMusic_profile_title() {
        return music_profile_title;
    }


}
