package com.hyunyong.myapplication.view;

import android.os.Bundle;
import android.view.MenuItem;

import com.hyunyong.myapplication.R;
import com.hyunyong.myapplication.data.Step;
import com.hyunyong.myapplication.db.AppDataBase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

public class RecipeActivity extends AppCompatActivity {
    static final String ID = "id";
    AppBarConfiguration appBarConfiguration;
    NavController mNavController;
    int recipeId = 0;
    boolean mTwoPane = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeId = getIntent().getIntExtra(ID, 0);
        setContentView(R.layout.navigation_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (findViewById(R.id.recipe_two_pain_layout) == null) {
            NavHostFragment host = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.my_nav_host_fragment);

            mNavController = host.getNavController();
            appBarConfiguration = new AppBarConfiguration.Builder(mNavController.getGraph()).build();
            setupActionBar(mNavController, appBarConfiguration);
        } else {
            mTwoPane = true;
            if(savedInstanceState == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();

                RecipeFragment menuFragment = new RecipeFragment();
                menuFragment.setID(recipeId);
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_menu_list, menuFragment)
                        .commit();

                AppDataBase.getDatabase(this).stepDao().getLiveStep(0).observe(this, new Observer<Step>() {
                    @Override
                    public void onChanged(Step step) {
                        Bundle args = new Bundle();
                        args.putInt(ViewRecipeFragment.ID, step.getId());
                        args.putString(ViewRecipeFragment.DESCRIPTION, step.getDescription());
                        args.putString(ViewRecipeFragment.VIDEO_URL, step.getVideoURL());
                        args.putString(ViewRecipeFragment.THUMBNAIL_URL, step.getThumbnailURL());
                        ViewRecipeFragment viewRecipeFragment = new ViewRecipeFragment();
                        fragmentManager.beginTransaction()
                                .add(R.id.recipe_contents, viewRecipeFragment)
                                .commit();
                    }
                });
            }
        }
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
