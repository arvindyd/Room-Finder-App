package com.radhe.roomfinder.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.radhe.roomfinder.Activities.ShowImageActivity;
import com.radhe.roomfinder.Models.Post;
import com.radhe.roomfinder.databinding.FragmentPostBinding;

import java.util.Date;


public class PostFragment extends Fragment {

    FragmentPostBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri uri;
    ProgressDialog dialog;

    public PostFragment() {
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
        binding = FragmentPostBinding.inflate(inflater, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        dialog = new ProgressDialog(getContext());
        dialog.setTitle("Data Uploading");
        dialog.setMessage("data uploading on database");


        binding.upSingleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 7);
            }
        });

        binding.imgSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(),ShowImageActivity.class);
                startActivity(intent);
            }
        });

        binding.btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkValidation();

            }
        });


        return binding.getRoot();


    }


    private void checkValidation() {

        String price = binding.roomRate.getText().toString();
        String deposite = binding.deposit.getText().toString();
        String location = binding.roomLocation.getText().toString();
        String finishSt = binding.finishStatus.getText().toString();
        String bathSt = binding.bathStatus.getText().toString();
        String toiletSt = binding.toiletStatus.getText().toString();
        String park = binding.numPark.getText().toString();

        if (uri == null) {

            Toast.makeText(getContext(), "Please Upload Image", Toast.LENGTH_SHORT).show();

        } else if (price.isEmpty()) {
            binding.roomRate.setError("Provide room rate");
            binding.roomRate.requestFocus();
        } else if (deposite.isEmpty()) {
            binding.deposit.setError("Provide deposit price");
            binding.deposit.requestFocus();
        } else if (location.isEmpty()) {
            binding.roomLocation.setError("Provide location");
            binding.roomLocation.requestFocus();
        } else if (finishSt.isEmpty()) {
            binding.finishStatus.setError("Provide finishing status");
            binding.finishStatus.requestFocus();
        } else if (bathSt.isEmpty()) {
            binding.bathStatus.setError("Provide finishing status");
            binding.bathStatus.requestFocus();
        } else if (toiletSt.isEmpty()) {
            binding.toiletStatus.setError("Provide toilet status");
            binding.toiletStatus.requestFocus();
        } else if (park.isEmpty()) {
            binding.numPark.setError("Provide park status");
            binding.numPark.requestFocus();
        } else {


            dialog.show();
            uploadImage();
        }


    }


    private void uploadImage() {

        final StorageReference reference = storage.getReference().child("single_image")
                .child(FirebaseAuth.getInstance().getUid())
                .child(new Date().getTime() + "");
        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(getContext(), "Image is uploaded on storage", Toast.LENGTH_SHORT).show();

                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Post post = new Post();

                        post.setPostImg(uri.toString());
                        post.setPostedBy(FirebaseAuth.getInstance().getUid());
                        post.setPostedAt(new Date().getTime());
                        post.setPrice(Integer.parseInt(binding.roomRate.getText().toString()));
                        post.setDeposit(Integer.parseInt(binding.deposit.getText().toString()));
                        post.setLocation(binding.roomLocation.getText().toString());
                        post.setSize(binding.roomSize.getText().toString());
                        post.setFinshSt(binding.finishStatus.getText().toString());
                        post.setBathSt(binding.bathStatus.getText().toString());
                        post.setToilSt(binding.toiletStatus.getText().toString());
                        post.setPark(Integer.parseInt(binding.numPark.getText().toString()));

                        database.getReference().child("room_post")
                                .push()
                                .setValue(post)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        dialog.dismiss();
                                        Toast.makeText(getContext(), "room details insert on database", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getContext(), ShowImageActivity.class);
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getContext(), "image is not uploaded", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 7 && data != null) {

            uri = data.getData();
            binding.imgSingle.setImageURI(uri);
        } else {

            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
        }


    }


}