package com.example.spotifywrapped.ui.update_account;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.spotifywrapped.AppLoginActivity;
import com.example.spotifywrapped.Engine;
import com.example.spotifywrapped.R;
import com.example.spotifywrapped.StartActivity;
import com.example.spotifywrapped.databinding.FragmentUpdateAccountBinding;
import com.example.spotifywrapped.databinding.FragmentWrapBinding;

public class UpdateAccount extends Fragment {

    private UpdateAccountViewModel mViewModel;
    private FragmentUpdateAccountBinding binding;
    private Engine engine;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

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

        sharedPreferences = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String username = sharedPreferences.getString("username", "default");

        Button changeButton = view.findViewById(R.id.change_username_button);
        engine = new Engine();
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String newUsername = binding.changeUsernameEditText.getText().toString();
                String newPassword = binding.changePasswordEditText.getText().toString();
//                if (newUsername != null && !newUsername.equals("")) {
//                    engine.setUsername(username, newUsername);
//                    Log.d("SET THE USERNAME", newUsername + " " + newPassword);
//                }
                if (newPassword != null && !newPassword.equals("")) {
                    engine.setPassword(username, newPassword);
                }

            }
        });

        Button deleteButton = view.findViewById(R.id.delete_account_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                engine.deleteUser(username);
                Log.d("AACCOUNT WAS DELETED", "onClick: account deleted");
                goToAppLoginActivity();
            }
        });
    }

    private void goToAppLoginActivity() {
        Intent intent = new Intent(getContext(), AppLoginActivity.class);
        startActivity(intent);
    }

}