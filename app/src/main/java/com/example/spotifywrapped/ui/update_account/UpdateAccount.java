package com.example.spotifywrapped.ui.update_account;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spotifywrapped.R;
import com.example.spotifywrapped.databinding.FragmentHistoryBinding;
import com.example.spotifywrapped.databinding.FragmentUpdateAccountBinding;
import com.example.spotifywrapped.ui.history.HistoryFragment;
import com.example.spotifywrapped.ui.history.HistoryViewModel;

public class UpdateAccount extends Fragment {

    private UpdateAccountViewModel mViewModel;
    private FragmentUpdateAccountBinding binding;

    public UpdateAccount() {}

    public static UpdateAccount newInstance() {
        return new UpdateAccount();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        UpdateAccountViewModel updateAccountViewModel =
                new ViewModelProvider(this).get(UpdateAccountViewModel.class);
        binding = FragmentUpdateAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(UpdateAccount.this)
                        .navigate(R.id.action_navigation_update_to_navigation_friends);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UpdateAccountViewModel.class);
        // TODO: Use the ViewModel
    }

}