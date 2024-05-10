package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.UserModel;
import com.example.myapplication.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.UserViewHolder> {
    private Context context;
    private ArrayList<UserModel> userList;

    public SearchAdapter(Context context, ArrayList<UserModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserModel user = userList.get(position);
        holder.bind(user);

        // Set text color for usernameTextView
        holder.usernameTextView.setTextColor(ContextCompat.getColor(context, R.color.yellow));
        // Set text color for emailTextView if needed
         holder.emailTextView.setTextColor(ContextCompat.getColor(context, R.color.yellow));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView usernameTextView;
        private TextView emailTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
        }

        public void bind(UserModel user) {
            usernameTextView.setText(user.getUsName());
            emailTextView.setText(user.getEmail());
        }
    }
}
