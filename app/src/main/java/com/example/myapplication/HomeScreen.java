package com.example.myapplication;


//import android.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.databinding.ActivityHomeScreenBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class HomeScreen extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton addButton;


    ActivityHomeScreenBinding binding;

//    TextView logout = (TextView)findViewById(R.id.logout);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeScreenBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavbar.setOnItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.search_bar:
                    replaceFragment(new SearchFragment());
                    break;
                case R.id.person:
                    UserFragment userFragment = new UserFragment();
                    String username = getIntent().getStringExtra("USERNAME");
                    userFragment.setLoggedInUsername(username); // Pass the logged-in username to the UserFragment
                    replaceFragment(userFragment);
                    break;
                case R.id.settings:
                    replaceFragment(new SettingsFragment());
                    break;
            }
            return true;
        });

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeScreen.this, loginPage.class);
//                startActivity(intent);
//            }
//        });

        // Initialize FloatingActionButton
        addButton = findViewById(R.id.AddPost);
        addButton.setOnClickListener(view -> {
            // Handle click event for the action button
            Intent intent = new Intent(HomeScreen.this, Addpost.class);
            String username = getIntent().getStringExtra("USERNAME");
            intent.putExtra("USERNAME", username);
            startActivity(intent);
        });
    }



    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_home, fragment);
        fragmentTransaction.commit();
    }
}