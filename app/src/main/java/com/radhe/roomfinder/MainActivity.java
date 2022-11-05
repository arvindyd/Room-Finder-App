package com.radhe.roomfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.radhe.roomfinder.Fragments.ChatsFragment;
import com.radhe.roomfinder.Fragments.HomeFragment;
import com.radhe.roomfinder.Fragments.PostFragment;
import com.radhe.roomfinder.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();


        binding.chipNavigationBar.setItemSelected(R.id.home,true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //  binding.toolbar.setVisibility(View.GONE);
        transaction.replace(R.id.container, new HomeFragment());
        transaction.commit();

        binding.chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (i){

                    case R.id.home:
                        // binding.toolbar.setVisibility(View.GONE);
                        transaction.replace(R.id.container, new HomeFragment());
                        break;

                    case R.id.post:
                        // binding.toolbar.setVisibility(View.GONE);
                       transaction.replace(R.id.container, new PostFragment());
                        break;

                    case R.id.chats:
                        // binding.toolbar.setVisibility(View.GONE);
                        transaction.replace(R.id.container, new ChatsFragment());
                        break;

                }
                transaction.commit();
            }
        });





    }
}