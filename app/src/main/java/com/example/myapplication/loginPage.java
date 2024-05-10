package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.security.AccessController;

public class loginPage extends AppCompatActivity {
    private EditText eUsername;
    private EditText ePassword;

    private Button eLoginbutton;


    private MyDataBaseHelper databaseHelper;

    private Button eSignupbutton;
    boolean isValid = false;
    private Button lButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.loginpage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            eUsername = findViewById(R.id.Lg_username);
            ePassword = findViewById(R.id.Lg_pass);
            eLoginbutton = findViewById(R.id.button);
            lButton = findViewById(R.id.LoginBack);
            databaseHelper = new MyDataBaseHelper(this);


            eLoginbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String username = eUsername.getText().toString();
                    String password = ePassword.getText().toString();
                    if(username.isEmpty() || password.isEmpty()) {
                        Toast.makeText(loginPage.this, "Please enter valid credentials", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean isValid = databaseHelper.checkCredentials(username, password);
                        if(isValid) {
                            Toast.makeText(loginPage.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(loginPage.this, HomeScreen.class);
                            intent.putExtra("USERNAME", username); // Pass the username to HomeScreen activity
                            startActivity(intent);
                        } else {
                            Toast.makeText(loginPage.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            lButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(loginPage.this, Startup_screen.class);
                    startActivity(intent);
                }
            });





            return insets;


        });

    }
    private boolean validate(String username, String password) {
        return databaseHelper.checkCredentials(username, password);
    }
}