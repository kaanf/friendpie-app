package com.example.friendpie.HelperAdapters.ProfileMusicGenres;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.friendpie.R;

import java.util.ArrayList;

public class ProfileMusicGenreAdapter extends RecyclerView.Adapter<ProfileMusicGenreViewHolder> {

    private ArrayList<ProfileMusic> music_title;

    public ProfileMusicGenreAdapter(ArrayList<ProfileMusic> music_title) {
        this.music_title = music_title;
    }

    @NonNull
    @Override
    public ProfileMusicGenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.profile_music_card, parent, false);
        ProfileMusicGenreViewHolder profileMusicGenreViewHolder = new ProfileMusicGenreViewHolder(view);
        return profileMusicGenreViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileMusicGenreViewHolder holder, int position) {
        holder.music_profile_title.setText(music_title.get(position).getMusic_title());
    }

    @Override
    public int getItemCount() {
        return music_title.size();
    }
}
