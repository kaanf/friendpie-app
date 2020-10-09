package com.example.friendpie.HelperAdapters.ProfileInterests;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendpie.R;

import java.util.ArrayList;

public class ProfileHobbyAdapter extends RecyclerView.Adapter<ProfileHobbyViewHolder> {

    private ArrayList<ProfileHobby> hobby_title;

    public ProfileHobbyAdapter(ArrayList<ProfileHobby> hobby_title) {
        this.hobby_title = hobby_title;
    }

    @NonNull
    @Override
    public ProfileHobbyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.profil_hobby_card, parent, false);
        ProfileHobbyViewHolder profileHobbyViewHolder = new ProfileHobbyViewHolder(view);
        return profileHobbyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileHobbyViewHolder holder, int position) {
        holder.hobby_profile_title.setText(hobby_title.get(position).getHobby_title());
    }

    @Override
    public int getItemCount() {
        return hobby_title.size();
    }
}
