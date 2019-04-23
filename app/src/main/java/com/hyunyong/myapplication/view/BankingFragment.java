package com.hyunyong.myapplication.view;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyunyong.myapplication.R;
import com.hyunyong.myapplication.data.Recipe;
import com.hyunyong.myapplication.db.AppDataBase;
import com.hyunyong.myapplication.db.dao.IngredientDao;
import com.hyunyong.myapplication.db.dao.RecipeDao;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import static android.os.Build.ID;
import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class BankingFragment extends Fragment {

    private boolean fromWidget;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            fromWidget = getActivity().getIntent().getBooleanExtra(IngredientWidget.INGREDIENTS, false);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_banking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.backing_recycler_view);
        AppDataBase dataBase = AppDataBase.getDatabase(getContext());
        RecipeDao recipeDao = dataBase.recipeDao();
        IngredientDao ingredientDao = dataBase.ingredientDao();

        List<Recipe> recipes = new ArrayList<>();
        BankingRecyclerViewAdapter adapter = new BankingRecyclerViewAdapter(
                recipes, (view1, item, position) -> {
            Bundle args = new Bundle();
            args.putInt(RecipeFragment.ID, item.getId());
            if (fromWidget) {
                ingredientDao.deleteAll();
                ingredientDao.insertAll(item.getIngredients());
                Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                Activity activity = getActivity();
                if (activity != null) {
                    intent.setComponent(new ComponentName(activity, IngredientWidget.class));
                    activity.sendBroadcast(intent);
                    activity.finish();
                }
            } else {
                findNavController(this).navigate(R.id.recipe, args);
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
