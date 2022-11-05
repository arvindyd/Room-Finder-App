package com.radhe.roomfinder.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.radhe.roomfinder.Adapters.ChatsUserAdapter;
import com.radhe.roomfinder.Adapters.PostAdapters;
import com.radhe.roomfinder.Models.Post;
import com.radhe.roomfinder.Models.Users;
import com.radhe.roomfinder.R;
import com.radhe.roomfinder.databinding.FragmentChatsBinding;


import java.util.ArrayList;


public class ChatsFragment extends Fragment {

    FragmentChatsBinding binding;
    ChatsUserAdapter adapters;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ArrayList<Users> list = new ArrayList<>();

    public ChatsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentChatsBinding.inflate(inflater, container, false);



        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


        adapters = new ChatsUserAdapter(list, getContext());
        binding.recyclerView2.setAdapter(adapters);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        binding.recyclerView2.setLayoutManager(manager);


        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    Users users = dataSnapshot.getValue(Users.class);
                    users.setUserId(dataSnapshot.getKey());
                    if (!users.getUserId().equals(FirebaseAuth.getInstance().getUid())){

                        list.add(users);
                    }

                }
                adapters.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return binding.getRoot();
    }
}