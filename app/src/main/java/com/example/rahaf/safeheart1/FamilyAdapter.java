package com.example.rahaf.safeheart1;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * RecyclerView.Adapter
 * RecyclerView.ViewHolder
 *
 */
public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.FamilyViewHolder>{

    private Context context;
    private List<Family> familyList;

    public FamilyAdapter(Context context, List<Family> familyList) {
        this.context = context;
        this.familyList = familyList;
    }

    @NonNull
    @Override
    public FamilyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_layout,null);
        return new FamilyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(FamilyViewHolder familyViewHolder, int position) {
        Family family = familyList.get(position);
        familyViewHolder.textViewTitle.setText(family.getName());
    }

    @Override
    public int getItemCount() {
        return familyList.size();


    }

    class FamilyViewHolder extends RecyclerView.ViewHolder {
        //ImageView imageView;
        TextView textViewTitle;
        public FamilyViewHolder(@NonNull View itemView) {
            super(itemView);
            //imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewName);

        }
    }


}
