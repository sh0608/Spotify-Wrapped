package com.example.spotifywrapped.ui.update_account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifywrapped.Engine;
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.User;
import com.example.spotifywrapped.databinding.FragmentFriendsBinding;
import com.example.spotifywrapped.databinding.FragmentUpdateAccountBinding;

import java.util.ArrayList;
import java.util.List;

public class Friends extends Fragment {
    private FriendsViewModel mViewModel;
    private FragmentFriendsBinding binding;
    Engine engine = new Engine();

    private List<User> friendsList = new ArrayList<>();
    public Friends() {}

    public static Friends newInstance() {
        return new Friends();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        UpdateAccountViewModel updateAccountViewModel =
                new ViewModelProvider(this).get(UpdateAccountViewModel.class);
        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        RecyclerView recyclerView = binding.usersRecyclerView;
        View root = binding.getRoot();
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Friends.this)
                        .navigate(R.id.action_navigation_friends_to_navigation_update);
            }
        });
        return root;
    }
}
