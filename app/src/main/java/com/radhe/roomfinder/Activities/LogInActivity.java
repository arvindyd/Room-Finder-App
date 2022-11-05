package com.radhe.roomfinder.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.radhe.roomfinder.MainActivity;
import com.radhe.roomfinder.SignUpActivity;
import com.radhe.roomfinder.WelComeActivity;
import com.radhe.roomfinder.databinding.ActivityLogInBinding;

public class LogInActivity extends AppCompatActivity {

    ActivityLogInBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(LogInActivity.this);
        dialog.setTitle("Please wait");
        dialog.setMessage("Login successfully");

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email= binding.userEmail.getText().toString();
                String passport= binding.userPassword.getText().toString();

                if (email.isEmpty()){
                    binding.userEmail.setError("Provide email");
                    binding.userEmail.requestFocus();
                }
                else  if (passport.isEmpty()){
                    binding.userPassword.setError("Provide password");
                    binding.userPassword.requestFocus();
                }
                else {

                    dialog.show();
                    signIn();


                }
            }
        });



        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(LogInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });



        if (auth.getCurrentUser()!=null){

            Intent intent= new Intent(LogInActivity.this,MainActivity.class);
            startActivity(intent);
        }


    }

    private void signIn() {

        auth.signInWithEmailAndPassword(binding.userEmail.getText().toString(),binding.userPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        dialog.dismiss();

                        if (task.isSuccessful()){

                            Toast.makeText(LogInActivity.this, "User login successfully", Toast.LENGTH_SHORT).show();

                            Intent intent= new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else {

                            Toast.makeText(LogInActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }


}