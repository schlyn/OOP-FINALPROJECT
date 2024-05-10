package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityFeedbacksBinding;

public class Feedbacks extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityFeedbacksBinding binding;

    private Button back;
    private Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityFeedbacksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        back = findViewById(R.id.Fback);
        submit = findViewById(R.id.Fsubmit);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Feedbacks.this, HomeScreen.class);
                startActivity(intent);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Feedbacks.this, "Feedbacks have been submitted", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Feedbacks.this, Feedbacks.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_feedbacks);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}