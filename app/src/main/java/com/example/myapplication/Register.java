package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class Register extends AppCompatActivity {
    private Button rSignup;
    private Button rlogin;
    private EditText rEmail;
    private EditText rUsername;
    private EditText rPass;
    private Button rBack;
    boolean isAllFieldsChecked = false;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            rSignup = findViewById(R.id.registerButton);
            rUsername = findViewById(R.id.REG_username);
            rEmail = findViewById(R.id.REG_email);
            rPass = findViewById(R.id.REG_PASS);
            rBack = findViewById(R.id.RegisterBack);


            rSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyDataBaseHelper myDB = new MyDataBaseHelper(Register.this);
                    myDB.Registration(rUsername.getText().toString().trim(),
                            rEmail.getText().toString().trim(),
                            rPass.getText().toString());


                    isAllFieldsChecked = CheckAllFields();

                    // the boolean variable turns to be true then
                    // only the user must be proceed to the activity2
                    if (isAllFieldsChecked) {
                        Intent i = new Intent(Register.this, loginPage.class);
                        Toast.makeText(Register.this, "Register Succesful", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                    }


//                    Intent intent = new Intent(Register.this, HomeScreen.class);
//                    startActivity(intent);
                }
            });


            rBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Register.this, Startup_screen.class);
                    startActivity(intent);
                }
            });


            return insets;

        });

    }

    private boolean CheckAllFields() {
        if (rUsername.length() == 0) {
            rUsername.setError("This field is required");
            return false;
        }

        if (rEmail.length() == 0) {
            rEmail.setError("This field is required");
            return false;
        } else if (!rEmail.getText().toString().endsWith("@gmail.com")) {
            rEmail.setError("Invalid Email Address");
            return false;
        }

            if (rPass.length() == 0) {
                rPass.setError("Password is required");
                return false;
            } else if (rPass.length() < 8) {
                rPass.setError("Password must be minimum 8 characters");
                return false;
            }

            // after all validation return true.
            return true;
        }
    }
