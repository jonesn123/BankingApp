package com.hyunyong.myapplication.view;

import android.os.Bundle;
import android.view.MenuItem;

import com.hyunyong.myapplication.R;
import com.hyunyong.myapplication.viewModel.MainViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.work.WorkInfo;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

public class MainActivity extends AppCompatActivity {

    AppBarConfiguration appBarConfiguration;
    NavController mNavController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.navigation_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavHostFragment host = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.my_nav_host_fragment);

        mNavController = host.getNavController();
        appBarConfiguration = new AppBarConfiguration.Builder(mNavController.getGraph()).build();
        setupActionBar(mNavController, appBarConfiguration);


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mNavController.navigateUp();
        return super.onOptionsItemSelected(item);
    }
}
