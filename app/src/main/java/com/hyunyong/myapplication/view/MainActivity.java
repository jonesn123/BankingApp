package com.hyunyong.myapplication.view;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.hyunyong.myapplication.R;

import androidx.navigation.fragment.NavHostFragment;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

public class MainActivity extends AppCompatActivity {

    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.navigation_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavHostFragment host = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.my_nav_host_fragment);

        NavController navController = host.getNavController();
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        setupActionBar(navController, appBarConfiguration);


        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                Toast.makeText(MainActivity.this, "id" + getResources().getResourceName(destination.getId()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupActionBar(NavController navController,
                                AppBarConfiguration appBarConfiguration) {
        setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

}
