package com.radhe.roomfinder.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.radhe.roomfinder.Models.Post;
import com.radhe.roomfinder.R;
import com.radhe.roomfinder.RoomDetailsActivity;
import com.radhe.roomfinder.databinding.SampleRoomBinding;


import java.util.ArrayList;

public class PostAdapters extends RecyclerView.Adapter<PostAdapters.viewHolder>{

    ArrayList<Post>list;
    Context context;

    public PostAdapters(ArrayList<Post> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.sample_room,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        final Post post= list.get(position);

        Glide.with(context)
                .load(post.getPostImg())
                .placeholder(R.drawable.placeholder)
                .into(holder.binding.roomImage);

        holder.binding.adress.setText(post.getLocation());
        holder.binding.rate.setText(post.getPrice()+"");

        String time = TimeAgo.using(post.getPostedAt());
        holder.binding.time.setText(time);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(context,RoomDetailsActivity.class);
                intent.putExtra("postId",post.getPostId());
                intent.putExtra("postedBy",post.getPostedBy());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

       SampleRoomBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

           binding= SampleRoomBinding.bind(itemView);
        }
    }


}
