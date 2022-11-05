package com.radhe.roomfinder;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.radhe.roomfinder.Models.Users;
import com.radhe.roomfinder.databinding.ActivitySignupBinding;


public class SignUpActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    ProgressDialog dialog;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        dialog = new ProgressDialog(SignUpActivity.this);
        dialog.setTitle("Creating  account");
        dialog.setMessage("We are creating your account");


        binding.impImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 7);
            }
        });


        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                checkValidation();


            }
        });


    }

    private void checkValidation() {


        String name= binding.agName.getText().toString();
        String email= binding.agEmail.getText().toString();
        String password= binding.agPassword.getText().toString();
        String number= binding.agMobile.getText().toString();
        String location= binding.agAdress.getText().toString();


        if (uri==null){

            Toast.makeText(this, "please upload image", Toast.LENGTH_SHORT).show();
        }
        else  if (name.isEmpty()){
            binding.agName.setError("Provide your name");
            binding.agName.requestFocus();
        }
        else  if (email.isEmpty()){
            binding.agEmail.setError("Provide email");
            binding.agEmail.requestFocus();
        }
        else  if (password.isEmpty()){
            binding.agPassword.setError("Provide password");
            binding.agPassword.requestFocus();
        }
        else  if (number.isEmpty()){
            binding.agMobile.setError("Provide mobile number");
            binding.agMobile.requestFocus();
        }
        else  if (location.isEmpty()){
            binding.agAdress.setError("Provide location");
            binding.agAdress.requestFocus();
        }
        else {

            dialog.show();
            signUp();
        }


    }

    private void signUp() {


        mAuth.createUserWithEmailAndPassword(binding.agEmail.getText().toString(), binding.agPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            final StorageReference reference = storage.getReference().child("user_profile")
                                    .child(FirebaseAuth.getInstance().getUid());

                            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    dialog.dismiss();
                                    Users users = new Users(uri.toString(), binding.agName.getText().toString(), binding.agEmail.getText().toString(),
                                            binding.agPassword.getText().toString(), binding.agAdress.getText().toString(),
                                            Long.parseLong(binding.agMobile.getText().toString()));

                                    String id = task.getResult().getUser().getUid();

                                    database.getReference().child("users").child(id).setValue(users);

                                    Toast.makeText(SignUpActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                        } else {

                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 7 && data != null) {

            uri = data.getData();
            binding.agentProfile.setImageURI(uri);
            Toast.makeText(this, "image is selected", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this, "image is empty", Toast.LENGTH_SHORT).show();
        }
    }

    //    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            reload();
//        }
//    }
//
//    private void reload() {
//
//        Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
//    }
}