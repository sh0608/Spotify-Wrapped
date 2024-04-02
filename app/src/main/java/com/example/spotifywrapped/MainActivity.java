package com.example.spotifywrapped;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.spotifywrapped.databinding.ActivityMainBinding;
import com.example.spotifywrapped.ui.SpotifyApiHelper;
import com.example.spotifywrapped.ui.history.HistoryFragment;
import com.example.spotifywrapped.ui.wrap.TopSongAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.spotifywrapped.ui.history.HistoryFragment;
import com.example.spotifywrapped.ui.history.HistoryOneMonth;
import com.example.spotifywrapped.ui.history.HistoryOneMonthViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Switch theme_switch;
    private Button button_month;
    SharedPreferences sharedPreferences = null;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        // NAV BAR SETUP
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_wrap, R.id.navigation_history, R.id.navigation_update, R.id.navigation_history_one_month, R.id.navigation_history_six_month, R.id.navigation_history_all_time)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        // THEME SWITCHER SETUP
        theme_switch = findViewById(R.id.switch_theme);
        sharedPreferences = getSharedPreferences("night", 0);
        Boolean switch_theme_flag = sharedPreferences.getBoolean("night_mode", true);
        if (switch_theme_flag) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            theme_switch.setChecked(true);
        }

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // Check the destination ID and update the navigation bar color accordingly
            if (destination.getId() == R.id.navigation_history|| destination.getId() == R.id.navigation_history_one_month ||
                    destination.getId() == R.id.navigation_history_six_month ||
                    destination.getId() == R.id.navigation_history_all_time) {
                navView.getMenu().findItem(R.id.navigation_history).setChecked(true);

            }
        });

            // THEME SWITCHER SETUP
            theme_switch = findViewById(R.id.switch_theme);
            sharedPreferences = getSharedPreferences("night", 0);
            Boolean switch_theme_flag = sharedPreferences.getBoolean("night_mode", true);
            if (switch_theme_flag) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                theme_switch.setChecked(true);
            }

            theme_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        theme_switch.setChecked(true);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("night_mode", true);
                        editor.apply();
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        theme_switch.setChecked(false);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("night_mode", false);
                        editor.apply();
                    }
                }
            });
    }
}