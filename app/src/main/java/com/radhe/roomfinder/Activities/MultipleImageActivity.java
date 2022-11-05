package com.radhe.roomfinder.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.radhe.roomfinder.MainActivity;
import com.radhe.roomfinder.Models.SliderModel;
import com.radhe.roomfinder.databinding.ActivityMultipleImageBinding;


import java.util.Date;

public class MultipleImageActivity extends AppCompatActivity {

    ActivityMultipleImageBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Intent intent;
    String postId;
    Uri mUri;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMultipleImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        intent = getIntent();
        postId = intent.getStringExtra("postId");


        dialog = new ProgressDialog(MultipleImageActivity.this);
        dialog.setTitle("Data Uploading");
        dialog.setMessage("data uploading on database");


        binding.importImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 11);
            }
        });


        binding.mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mUri == null) {

                    Toast.makeText(MultipleImageActivity.this, "Select image", Toast.LENGTH_SHORT).show();
                } else {

                    dialog.show();
                    final StorageReference reference1 = storage.getReference().child("slider_image")
                            .child(FirebaseAuth.getInstance().getUid())
                            .child(new Date().getTime() + "");
                    reference1.putFile(mUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(MultipleImageActivity.this, "multiple image is uploaded", Toast.LENGTH_SHORT).show();

                            reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    SliderModel sliderModel = new SliderModel();
                                    sliderModel.setUrl(uri.toString());

                                    database.getReference()
                                            .child("room_post").child(postId).child("slider")
                                            .push()
                                            .setValue(sliderModel)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                    dialog.dismiss();
                                                    Toast.makeText(MultipleImageActivity.this, "press back and go home", Toast.LENGTH_SHORT).show();

                                                }

                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Toast.makeText(MultipleImageActivity.this, "image not uploaded database", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(MultipleImageActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });


                }


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 11 && data != null) {

            mUri = data.getData();
            binding.mRoom.setImageURI(mUri);


        } else {

            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(MultipleImageActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}