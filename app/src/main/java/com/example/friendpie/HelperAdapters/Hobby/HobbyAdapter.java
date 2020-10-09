package com.example.friendpie.HelperAdapters.Hobby;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.friendpie.R;
import java.util.ArrayList;

public class HobbyAdapter extends RecyclerView.Adapter<HobbyViewHolder> {

    private ArrayList<Hobby> hobby;
    private ArrayList<String> selectedHobbies = new ArrayList<>();


    public HobbyAdapter(ArrayList<Hobby> hobby) {
        this.hobby = hobby;

    }

    @NonNull
    @Override
    public HobbyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.hobby_card, parent, false);
        HobbyViewHolder hobbyViewHolder = new HobbyViewHolder(view);
        return hobbyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final HobbyViewHolder holder, final int position) {
        holder.hobby_checkbox.setText(hobby.get(position).getHobbyTitle());
        holder.hobby_checkbox.setCompoundDrawablesWithIntrinsicBounds(hobby.get(position).getImageId(), 0,0,0);
        holder.hobby_checkbox.setOnCheckedChangeListener(null);
        holder.hobby_checkbox.setChecked(hobby.get(position).isSelected());
        holder.hobby_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                hobby.get(position).setSelected(b);
                if(b){
                    selectedHobbies.add(hobby.get(position).getHobbyTitle());
                }else{
                    selectedHobbies.remove(hobby.get(position).getHobbyTitle());
                }
            }
        });
    }

    @Override
    public int getItemCount() { return hobby.size(); }

    public void filterList(ArrayList<Hobby> filteredList) {
        hobby = filteredList;
        notifyDataSetChanged();
    }

    public ArrayList<String> listOfHobbies() { return selectedHobbies; }

}
