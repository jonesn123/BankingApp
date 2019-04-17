package com.hyunyong.myapplication.view;

import android.os.Bundle;
import android.widget.Toast;

import com.hyunyong.myapplication.R;
import com.hyunyong.myapplication.viewModel.MainViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.work.WorkInfo;

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

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.getMutableLiveData().observe(this, workInfo -> {
            if (workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED) {

            }
        });
        viewModel.fetchFromNetwork();
    }

    private void setupActionBar(NavController navController,
                                AppBarConfiguration appBarConfiguration) {
        setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

}
