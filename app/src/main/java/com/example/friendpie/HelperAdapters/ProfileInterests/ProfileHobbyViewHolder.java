package com.example.friendpie.HelperAdapters.ProfileInterests;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendpie.R;

public class ProfileHobbyViewHolder extends RecyclerView.ViewHolder {

    TextView hobby_profile_title;

    public ProfileHobbyViewHolder(@NonNull View itemView) {
        super(itemView);

        hobby_profile_title = itemView.findViewById(R.id.hobby_profile_title);
    }

    public TextView getHobby_profile_title() {
        return hobby_profile_title;
    }
}
