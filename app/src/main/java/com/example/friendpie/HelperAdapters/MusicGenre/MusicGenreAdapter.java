package com.example.friendpie.HelperAdapters.MusicGenre;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.friendpie.R;
import java.util.ArrayList;

public class MusicGenreAdapter extends RecyclerView.Adapter<MusicGenreViewHolder> {

    ArrayList<MusicGenre> musicGenreArrayList;
    ArrayList<String> selectedGenres = new ArrayList<>();
    int backgroundPosition = 0;

    int [] backgroundDrawable = {

            R.drawable.music_genre_background_selector,
            R.drawable.music_genre_background_selector_2,
            R.drawable.music_genre_background_selector_3,
            R.drawable.music_genre_background_selector_4,
            R.drawable.music_genre_background_selector_5

    };

    public MusicGenreAdapter(ArrayList<MusicGenre> musicGenreArrayList) { this.musicGenreArrayList = musicGenreArrayList; }

    @NonNull
    @Override
    public MusicGenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.music_genres, parent, false);
        MusicGenreViewHolder musicGenreViewHolder = new MusicGenreViewHolder(view);
        return musicGenreViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MusicGenreViewHolder holder, final int position) {

        holder.musicGenre_checkbox.setText(musicGenreArrayList.get(position).getMusicGenreTitle());
        holder.musicGenre_checkbox.setBackgroundResource(backgroundDrawable[backgroundPosition]);
        holder.musicGenre_checkbox.setOnCheckedChangeListener(null);
        holder.musicGenre_checkbox.setChecked(musicGenreArrayList.get(position).isSelected());
        holder.musicGenre_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                musicGenreArrayList.get(position).setSelected(b);
                if(b){
                    selectedGenres.add(musicGenreArrayList.get(position).getMusicGenreTitle());
                }else{
                    selectedGenres.remove(musicGenreArrayList.get(position).getMusicGenreTitle());
                }
            }
        });

        backgroundPosition++;
        if(backgroundPosition==5){
            backgroundPosition=0;
        }
    }

    @Override
    public int getItemCount() { return musicGenreArrayList.size(); }

    public ArrayList<String> listOfMusicGenres() { return selectedGenres; }

}
