package com.example.spotifywrapped;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.example.spotifywrapped.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private Switch theme_switch;
    SharedPreferences sharedPreferences = null;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.spotifywrapped.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
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
        boolean switch_theme_flag = sharedPreferences.getBoolean("night_mode", true);
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