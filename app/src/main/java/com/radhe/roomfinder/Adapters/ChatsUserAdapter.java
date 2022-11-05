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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radhe.roomfinder.Activities.ChatsDetailsActivity;
import com.radhe.roomfinder.Models.Chats;
import com.radhe.roomfinder.Models.Post;
import com.radhe.roomfinder.Models.Users;
import com.radhe.roomfinder.R;
import com.radhe.roomfinder.databinding.ChatsUserSampBinding;


import java.util.ArrayList;

public class ChatsUserAdapter extends RecyclerView.Adapter<ChatsUserAdapter.viewHolder>{

    ArrayList<Users>list;
    Context context;

    public ChatsUserAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.chats_user_samp,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Users users = list.get(position);

        Glide.with(context)
                .load(users.getUserProfile())
                .placeholder(R.drawable.placeholder)
                .into(holder.binding.user);

        holder.binding.userName.setText(users.getUserName());



        FirebaseDatabase.getInstance().getReference().child("chats")
                .child(FirebaseAuth.getInstance().getUid() + users.getUserId())
                .orderByChild("timestamp")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.hasChildren()) {

                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                holder.binding.lastMessage.setText(snapshot1.child("message").getValue().toString());
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ChatsDetailsActivity.class);
                intent.putExtra("userName", users.getUserName());
                intent.putExtra("userId", users.getUserId());
                intent.putExtra("profilePic", users.getUserProfile());
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class viewHolder extends RecyclerView.ViewHolder {
        ChatsUserSampBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding= ChatsUserSampBinding.bind(itemView);
        }
    }
}
