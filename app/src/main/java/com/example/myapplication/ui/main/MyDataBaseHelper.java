package com.example.myapplication.ui.main;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.Register;
import com.example.myapplication.loginPage;

class MyDataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "GO4L.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "GO4L_USER";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USERNAME = "Username";
    private static final String COLUMN_EMAIL = "EMAIL";
    private static final String COLUMN_PASSWORD = "Password";
    public MyDataBaseHelper(@Nullable Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE" + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_USERNAME + " VARCHAR (255) PRIMARY KEY, " +
                        COLUMN_EMAIL + " VARCHAR (255) NOT NULL , " +
                        COLUMN_PASSWORD + " VARCHAR (255) NOT NULL );";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    void Registration(String username, String password, String email){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1){
            Toast.makeText(context, "REGISTRATION FAILED", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show();
        }
    }
}
