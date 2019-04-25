package com.hyunyong.myapplication.view;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.hyunyong.myapplication.R;
import com.hyunyong.myapplication.data.Recipe;
import com.hyunyong.myapplication.db.AppDataBase;
import com.hyunyong.myapplication.db.dao.IngredientDao;
import com.hyunyong.myapplication.db.dao.RecipeDao;
import com.hyunyong.myapplication.viewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.WorkInfo;

public class MainActivity extends AppCompatActivity {


    private boolean fromWidget;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fromWidget = getIntent().getBooleanExtra(IngredientWidget.INGREDIENTS, false);
        setContentView(R.layout.activity_banking);

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.getMutableLiveData().observe(this, workInfo -> {
            if (workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED) {

            }
        });
        viewModel.fetchFromNetwork();


        RecyclerView recyclerView = findViewById(R.id.backing_recycler_view);
        AppDataBase dataBase = AppDataBase.getDatabase(this);
        RecipeDao recipeDao = dataBase.recipeDao();
        IngredientDao ingredientDao = dataBase.ingredientDao();

        List<Recipe> recipes = new ArrayList<>();
        BankingRecyclerViewAdapter adapter = new BankingRecyclerViewAdapter(
                recipes, (view1, item, position) -> {
            if (fromWidget) {
                ingredientDao.deleteAll();
                ingredientDao.insertAll(item.getIngredients());
                Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.setComponent(new ComponentName(this, IngredientWidget.class));
                sendBroadcast(intent);
                finish();
            } else {
                Intent intent = new Intent(this, RecipeActivity.class);
                intent.putExtra(RecipeActivity.ID, item.getId());
                startActivity(intent);
            }
        });
        recipeDao.getRecipes().observe(this, items -> {
            recipes.clear();
            recipes.addAll(items);
            adapter.notifyDataSetChanged();
        });

        recyclerView.setAdapter(adapter);
    }
}
