package com.radhe.roomfinder.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.radhe.roomfinder.Activities.MultipleImageActivity;
import com.radhe.roomfinder.Models.Post;
import com.radhe.roomfinder.R;
import com.radhe.roomfinder.databinding.SampleShowimageBinding;

import java.util.ArrayList;

public class ShowImgAdapter extends RecyclerView.Adapter<ShowImgAdapter.imageViewHolder> {

    ArrayList<Post> list;
    Context context;

    public ShowImgAdapter(ArrayList<Post> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public imageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.sample_showimage,parent,false);

        return new imageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull imageViewHolder holder, int position) {


        final Post post= list.get(position);

        Glide.with(context)
                .load(post.getPostImg())
                .placeholder(R.drawable.placeholder)
                .into(holder.binding.room);

        holder.binding.roomAdress.setText(post.getLocation());
        holder.binding.roomPrice.setText(post.getPrice()+"");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(context, MultipleImageActivity.class);
                intent.putExtra("postId",post.getPostId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class imageViewHolder extends RecyclerView.ViewHolder {

        SampleShowimageBinding binding;
        public imageViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = SampleShowimageBinding.bind(itemView);
        }
    }
}
