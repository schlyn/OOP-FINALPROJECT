package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.UserModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import androidx.appcompat.widget.SearchView;

public class SearchFragment extends Fragment {
    private MyDataBaseHelper dbHelper;
    private SearchAdapter searchAdapter;
    private ArrayList<UserModel> store_User_Model_Info = new ArrayList<>();
    private FragmentSearchBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new MyDataBaseHelper(getContext());

        searchAdapter = new SearchAdapter(getContext(), store_User_Model_Info);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(searchAdapter);
        SearchView searchView = binding.searchView;
        EditText editText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        editText.setTextColor(getResources().getColor(R.color.yellow));
        // Setup search functionality
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchUsers(newText);
                return true;
            }
        });
    }

    private void searchUsers(String keyword) {
        store_User_Model_Info.clear();
        if (!keyword.isEmpty()) {
            ArrayList<UserModel> users = dbHelper.searchUsers(keyword);
            store_User_Model_Info.addAll(users);
        } else {
            ArrayList<UserModel> allUsers = dbHelper.getAllUsers();
            store_User_Model_Info.addAll(allUsers);
        }
        searchAdapter.notifyDataSetChanged();
    }
}
