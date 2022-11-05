package com.radhe.roomfinder.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.radhe.roomfinder.Models.SewaModel;
import com.radhe.roomfinder.R;
import com.radhe.roomfinder.databinding.SameThingsBinding;

import java.util.ArrayList;

public class SewaAdapter extends RecyclerView.Adapter<SewaAdapter.viewHolder> {

    ArrayList<SewaModel> list;
    Context context;

    public SewaAdapter(ArrayList<SewaModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.same_things,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        final SewaModel sewaModel = list.get(position);
        holder.binding.sewa.setText(sewaModel.getSewa());
        holder.binding.seName.setText(sewaModel.getTitle());
        holder.binding.sProfile.setImageResource(sewaModel.getImage());
        holder.binding.km.setText(sewaModel.getKm()+"");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        SameThingsBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding= SameThingsBinding.bind(itemView);
        }
    }

}
