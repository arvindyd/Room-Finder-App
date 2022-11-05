package com.radhe.roomfinder.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radhe.roomfinder.Adapters.PostAdapters;
import com.radhe.roomfinder.Adapters.ShowImgAdapter;
import com.radhe.roomfinder.Models.Post;
import com.radhe.roomfinder.R;
import com.radhe.roomfinder.databinding.ActivityShowImageBinding;

import java.util.ArrayList;

public class ShowImageActivity extends AppCompatActivity {

    ActivityShowImageBinding binding;
    ShowImgAdapter adapters;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ArrayList<Post> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


        adapters = new ShowImgAdapter(list,this);
        binding.recyShowImg.setAdapter(adapters);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.recyShowImg.setLayoutManager(manager);



        database.getReference().child("room_post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    Post post = dataSnapshot.getValue(Post.class);
                    post.setPostId(dataSnapshot.getKey());
                    list.add(post);
                }
                adapters.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}