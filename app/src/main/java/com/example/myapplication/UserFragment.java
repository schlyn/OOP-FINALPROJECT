package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button editProfile;

    private ImageView imageViewProfile;
//    private String textViewUsername;
    private Button buttonEditProfile;
    private RecyclerView recyclerViewPosts;
    private PostAdapter postAdapter;
    private TextView textViewUsername;



    private String loggedInUsername; // Add this variable to hold the logged-in username

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        // Initialize views
        imageViewProfile = view.findViewById(R.id.imageView_profile);
        buttonEditProfile = view.findViewById(R.id.button_edit_profile);
        recyclerViewPosts = view.findViewById(R.id.recyclerView_posts);
        textViewUsername = view.findViewById(R.id.textView_username);

        // Set up RecyclerView
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        postAdapter = new PostAdapter();
        recyclerViewPosts.setAdapter(postAdapter);
        textViewUsername.setText(loggedInUsername);
        textViewUsername.setTextColor(getResources().getColor(R.color.your_color));

        editProfile = view.findViewById(R.id.button_edit_profile);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Profile.class);
                startActivity(intent);
            }
        });


        // Fetch and display user's posts
        fetchUserPosts(loggedInUsername); // Pass the logged-in username here

        return view;

    }

    private void fetchUserPosts(String username) {
        // Fetch posts from database based on the provided username
        MyDataBaseHelper dbHelper = new MyDataBaseHelper(getContext());
        ArrayList<PostModel> userPosts = dbHelper.getUserPosts(username);

        // Update RecyclerView with user's posts
        postAdapter.setPosts(userPosts);
        postAdapter.notifyDataSetChanged();
    }

    // Method to set the logged-in username
    public void setLoggedInUsername(String username) {
        loggedInUsername = username;

    }
}
