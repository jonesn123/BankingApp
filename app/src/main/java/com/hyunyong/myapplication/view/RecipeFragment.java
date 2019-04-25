package com.hyunyong.myapplication.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyunyong.myapplication.R;
import com.hyunyong.myapplication.data.Ingredient;
import com.hyunyong.myapplication.data.Step;
import com.hyunyong.myapplication.db.AppDataBase;
import com.hyunyong.myapplication.db.IngredientConverters;
import com.hyunyong.myapplication.db.StepConverters;
import com.hyunyong.myapplication.db.dao.RecipeDao;
import com.hyunyong.myapplication.db.dao.StepDao;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.navigation.fragment.NavHostFragment.findNavController;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class RecipeFragment extends Fragment {

    private int mID = 1;

    public void setID(int id) {
        this.mID = id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecipeActivity recipeActivity = (RecipeActivity) getActivity();
        AppDataBase dataBase = AppDataBase.getDatabase(getContext());
        RecipeDao recipeDao = dataBase.recipeDao();
        StepDao stepDao = dataBase.stepDao();
        List<Ingredient> ingredients = new ArrayList<>();
        RecyclerView ingredientRecyclerView = view.findViewById(R.id.ingredient_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.HORIZONTAL);
        ingredientRecyclerView.setLayoutManager(manager);

        IngredientRecyclerViewAdapter ingredientRecyclerViewAdapter = new IngredientRecyclerViewAdapter(ingredients);
        ingredientRecyclerView.setAdapter(ingredientRecyclerViewAdapter);
        recipeDao.getIngredients(mID).observe(this, str -> {
            ingredients.clear();
            ingredients.addAll(IngredientConverters.fromString(str));
            ingredientRecyclerViewAdapter.notifyDataSetChanged();
        });

        RecyclerView recipeRecyclerView = view.findViewById(R.id.recipe_recycler_view);
        List<Step> steps = new ArrayList<>();
        RecipeRecyclerViewAdapter recipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(steps,
                (view1, item, position) -> {
                    Bundle args = new Bundle();
                    args.putInt(ViewRecipeFragment.ID, item.getId());
                    args.putString(ViewRecipeFragment.DESCRIPTION, item.getDescription());
                    args.putString(ViewRecipeFragment.VIDEO_URL, item.getVideoURL());
                    args.putString(ViewRecipeFragment.THUMBNAIL_URL, item.getThumbnailURL());
                    if (recipeActivity.mTwoPane) {
                        ViewRecipeFragment viewRecipeFragment = new ViewRecipeFragment();
                        viewRecipeFragment.setArguments(args);
                        getFragmentManager().beginTransaction().replace(R.id.recipe_contents, viewRecipeFragment).commit();
                    } else {
                        findNavController(this).navigate(R.id.view_recipe, args);
                    }
                });

        recipeRecyclerView.setAdapter(recipeRecyclerViewAdapter);

        recipeDao.getSteps(mID).observe(this, str -> {
            steps.clear();
            steps.addAll(StepConverters.fromString(str));
            stepDao.deleteAll();
            stepDao.insertAll(steps);
            recipeRecyclerViewAdapter.notifyDataSetChanged();
        });
    }
}
