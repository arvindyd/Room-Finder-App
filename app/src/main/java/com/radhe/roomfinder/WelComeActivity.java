package com.radhe.roomfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.radhe.roomfinder.databinding.ActivityWelComeBinding;

public class WelComeActivity extends AppCompatActivity {

    ActivityWelComeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityWelComeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        binding.searchCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(WelComeActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });



    }
}