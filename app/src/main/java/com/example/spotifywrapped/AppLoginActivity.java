package com.example.spotifywrapped;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.spotifywrapped.databinding.ActivityAppLoginBinding;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AppLoginActivity extends AppCompatActivity {

    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private ActivityAppLoginBinding binding;
    private Engine engine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        engine = new Engine();
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        EditText usernameEditText = binding.usernameEditText;
        EditText passwordEditText = binding.passwordEditText;
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
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
            }
        });

        binding.createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                engine.addUser(username, password).thenAccept(userAdded -> {
                    if (userAdded) {
                        editor.putString("username", username);
                        editor.putString("password", password);
                        editor.apply();
                        goToSpotifyLogin();
                    } else {
                        Toast.makeText(AppLoginActivity.this,
                                "Account creation failed. Enter valid info or login to existing account!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    private void goToSpotifyLogin() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}