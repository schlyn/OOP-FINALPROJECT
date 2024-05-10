package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.example.myapplication.UserModel;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

class MyDataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "GO4L.db";
    private static final int DATABASE_VERSION = 5;
    public static final String TABLE_NAME = "GO4L_USER";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "Username";
    public static final String COLUMN_EMAIL = "EMAIL";
    private static final String COLUMN_PASSWORD = "Password";
    public static final String TABLE_POSTS = "Posts";
    public static final String COLUMN_POST_ID = "Post_id";
    public static final String COLUMN_CONTENT = "Content";
    public static final String COLUMN_POST_DATE = "Post_date";

    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String query =
                " CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_USERNAME + " VARCHAR(255) , " +
                        COLUMN_EMAIL + " VARCHAR(255) , " +
                        COLUMN_PASSWORD + " VARCHAR(255) )";


        String queryPosts =
                "CREATE TABLE " + TABLE_POSTS +
                        " (" + COLUMN_POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_USERNAME + " TEXT, " +
                        COLUMN_CONTENT + " TEXT, " +
                        COLUMN_POST_DATE + " TEXT)";


        db.execSQL(query);
        db.execSQL(queryPosts);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        switch (oldVersion) {
//            case 1:
//                // If the old version is 1, then we only need to create the Posts table
//                db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_POSTS +
//                        " (" + COLUMN_POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                        COLUMN_USERNAME + " TEXT, " +
//                        COLUMN_CONTENT + " TEXT, " +
//                        COLUMN_POST_DATE + " TEXT)");
//                // No break statement here, so if the old version is 1, it will also execute the case for version 2
//            case 2:
//                // Handle the creation of other tables if there's a version 2
//                break;
//            // Add more cases for future versions
//        }
        onCreate(db);

    }

    void Registration(String username, String email, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_EMAIL, email);

        long result = db.insert(TABLE_NAME, null, cv);

    }

    void Post(String userEmail, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String username = getUsernameFromEmail(userEmail);

        // Get current date and time
        String date = getCurrentTimeStamp();

        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_CONTENT, content);
        cv.put(COLUMN_POST_DATE, date);

        long result = db.insert(TABLE_POSTS, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Error adding post", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Post added successfully", Toast.LENGTH_SHORT).show();
        }
    }





    public boolean checkCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }


    Cursor readAllData() {

        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    private String getCurrentTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

//    Cursor getAllUsers() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.query(TABLE_NAME, null, null, null, null, null, null);
//    }


    private String getUsernameFromEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_USERNAME};
        String selection = COLUMN_EMAIL + "=?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        String username = null;
        if (cursor != null && cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
            cursor.close();
        }

        return username != null ? username : "DefaultUser";
    }

//    public Cursor searchUsers(String keyword) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] projection = {COLUMN_USERNAME, COLUMN_EMAIL};
//        String selection = COLUMN_USERNAME + " LIKE ? OR " + COLUMN_EMAIL + " LIKE ?";
//        String[] selectionArgs = {"%" + keyword + "%", "%" + keyword + "%"};
//        return db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
//    }

    @SuppressLint("Range")
    public ArrayList<UserModel> getAllUsers() {
        ArrayList<UserModel> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                UserModel user = new UserModel();
                user.setUsName(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
                // Add other user properties as needed
                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }
    public ArrayList<UserModel> searchUsers(String keyword) {
        ArrayList<UserModel> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USERNAME + " LIKE ? OR " + COLUMN_EMAIL + " LIKE ?";
        String[] selectionArgs = new String[]{"%" + keyword + "%", "%" + keyword + "%"};
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                UserModel user = new UserModel(username, email);
                users.add(user);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return users;
    }

    public ArrayList<PostModel> getAllPosts() {
        ArrayList<PostModel> posts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_POSTS, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
                String content = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_POST_DATE));
                PostModel post = new PostModel(username, content, date);
                posts.add(post);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return posts;
    }

    public ArrayList<PostModel> getUserPosts(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<PostModel> userPosts = new ArrayList<>();

        // Define your SQL query to fetch posts associated with the given username
        String selectQuery = "SELECT * FROM " + TABLE_POSTS + " WHERE " + COLUMN_USERNAME + " = ?";

        // Execute the query
        Cursor cursor = db.rawQuery(selectQuery, new String[]{username});

        // Loop through the cursor to extract posts
        if (cursor.moveToFirst()) {
            do {
                // Extract post details from cursor and create a PostModel object
                int postId = cursor.getInt(cursor.getColumnIndex(COLUMN_POST_ID));
                String postContent = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT));
                String postUsername = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));

                // Create a PostModel object and add it to the list
                String postIdString = String.valueOf(postId);
                PostModel post = new PostModel(postIdString, postContent, postUsername);
                userPosts.add(post);
            } while (cursor.moveToNext());
        }

        // Close cursor and database
        cursor.close();
        db.close();

        // Return the list of user posts
        return userPosts;
    }
}



