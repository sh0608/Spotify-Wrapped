package com.example.spotifywrapped.ui.update_account;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.spotifywrapped.AppLoginActivity;
import com.example.spotifywrapped.Engine;
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.databinding.FragmentUpdateAccountBinding;
import com.example.spotifywrapped.databinding.FragmentWrapBinding;

public class UpdateAccount extends Fragment {

    private UpdateAccountViewModel mViewModel;
    private FragmentUpdateAccountBinding binding;
    private Engine engine;

    public static UpdateAccount newInstance() {
        return new UpdateAccount();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUpdateAccountBinding.inflate(inflater, container, false);

        UpdateAccountViewModel updateAccountViewModel = new ViewModelProvider(this).get(UpdateAccountViewModel.class);
        View view = binding.getRoot();
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UpdateAccountViewModel updateAccountViewModel = new ViewModelProvider(this).get(UpdateAccountViewModel.class);

        Button changeButton = view.findViewById(R.id.change_username_button);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                engine.
                engine.checkLogin(username, password).thenAccept(loggedIn -> {
                    if (loggedIn) {
                        editor.putString("username", username);
                        editor.putString("password", password);
                        editor.apply();
                        goToSpotifyLogin();
                    } else {
                        Toast.makeText(AppLoginActivity.this,
                                "Login unsuccessful. Check your login information or create an account!",
                                Toast.LENGTH_SHORT).show();
                    }
                });

//                String url = "https://www.spotify.com/us/account/profile/";
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(url));
//                startActivity(intent);
            }
        });

        Button deleteButton = view.findViewById(R.id.delete_account_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url = "https://www.spotify.com/us/account/close/";
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(url));
//                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UpdateAccountViewModel.class);
        // TODO: Use the ViewModel
    }

}