package com.radhe.roomfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.radhe.roomfinder.Adapters.SewaAdapter;
import com.radhe.roomfinder.Models.Post;
import com.radhe.roomfinder.Models.SewaModel;
import com.radhe.roomfinder.Models.Users;
import com.radhe.roomfinder.databinding.ActivityRoomDetailsBinding;


import java.util.ArrayList;
import java.util.List;

public class RoomDetailsActivity extends AppCompatActivity {

    ActivityRoomDetailsBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Intent intent;
    String postId;
    String postedBy;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoomDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        intent = getIntent();
        postId = intent.getStringExtra("postId");
        postedBy = intent.getStringExtra("postedBy");


        ArrayList<SewaModel> list = new ArrayList<>();

        list.add(new SewaModel("Healthcare","B.P. Koirala Memorial \nCancer Hospital",R.drawable.ic_hospital,1.2));
        list.add(new SewaModel("Shopping Mall","Bhat-Bhateni Super \nMarket",R.drawable.ic_shopping,1));
        list.add(new SewaModel("Bank","Nepal Bank Limited is \nthe first commercial bank",R.drawable.ic_bank,1.3));
        list.add(new SewaModel("College","Makawanpur Multiple \nCampus, Hetauda",R.drawable.ic_school,0.5));


        SewaAdapter adapter = new SewaAdapter(list, this);
        binding.recyThings.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.recyThings.setLayoutManager(layoutManager);



        final List<SlideModel> remoteImage = new ArrayList<>();

        database.getReference().child("room_post").child(postId).child("slider").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data:snapshot.getChildren()){

                    remoteImage.add(new SlideModel(data.child("url").getValue().toString(), ScaleTypes.FIT));
                }

                binding.imageSliders.setImageList(remoteImage,ScaleTypes.FIT);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        database.getReference().child("room_post").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Post post = snapshot.getValue(Post.class);

                binding.price.setText(post.getPrice() + "");
                binding.rDeposite.setText(post.getDeposit() + "");
                binding.parking.setText(post.getPark() + "");
                binding.location.setText(post.getLocation());
                binding.roomSt.setText(post.getFinshSt());
                binding.bathSt.setText(post.getBathSt());
                binding.toiletSt.setText(post.getToilSt());

                String time = TimeAgo.using(post.getPostedAt());
                binding.postedTime.setText(time);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        database.getReference().child("users")
                .child(postedBy).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Users users = snapshot.getValue(Users.class);

                Glide.with(RoomDetailsActivity.this)
                        .load(users.getUserProfile())
                        .placeholder(R.drawable.placeholder)
                        .into(binding.agentProfile);
                binding.agentName.setText(users.getUserName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        binding.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database.getReference().child("users")
                        .child(postedBy).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Users users = snapshot.getValue(Users.class);

                        Uri uri = Uri.parse("geo:0,0?q=" + users.getLocation());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.setPackage("com.google.android.apps.maps");
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });



        binding.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database.getReference().child("users")
                        .child(postedBy).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Users users = snapshot.getValue(Users.class);

                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + users.getPhNumber()));
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });



    }


}