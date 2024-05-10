package com.example.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.example.myapplication.MyDataBaseHelper;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityAddpostBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Addpost extends AppCompatActivity {

    EditText postEditText;
    Button addPostButton;
    MyDataBaseHelper dbHelper;

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);

        postEditText = findViewById(R.id.POST_TEXT);
        addPostButton = findViewById(R.id.Addpost);
        back = findViewById(R.id.APback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Addpost.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        dbHelper = new MyDataBaseHelper(this);

        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPostToDatabase();
            }
        });
    }

    private void addPostToDatabase() {
        String postContent = postEditText.getText().toString().trim();
        if (postContent.isEmpty()) {
            Toast.makeText(this, "Please enter post content", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get username (if available)
        String username = getIntent().getStringExtra("USERNAME");
        if (username == null) {
            // Handle case where username is not available
            Toast.makeText(this, "Error: Username not available", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get current date and time
        String postDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        // Insert post into database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDataBaseHelper.COLUMN_USERNAME, username);
        values.put(MyDataBaseHelper.COLUMN_CONTENT, postContent);
        values.put(MyDataBaseHelper.COLUMN_POST_DATE, postDate);

        long newRowId = db.insert(MyDataBaseHelper.TABLE_POSTS, null, values);
        if (newRowId == -1) {
            Toast.makeText(this, "Error adding post", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Post added successfully", Toast.LENGTH_SHORT).show();
            finish(); // Finish activity after adding post
        }
    }
}
