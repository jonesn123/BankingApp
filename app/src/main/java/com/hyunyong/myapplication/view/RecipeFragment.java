package com.hyunyong.myapplication.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyunyong.myapplication.R;
import com.hyunyong.myapplication.data.Ingredient;
import com.hyunyong.myapplication.data.Step;
import com.hyunyong.myapplication.db.AppDataBase;
import com.hyunyong.myapplication.db.IngredientConverters;
import com.hyunyong.myapplication.db.StepConverters;
import com.hyunyong.myapplication.db.dao.RecipeDao;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class RecipeFragment extends Fragment {
    static final String ID = "id";

    private int mID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mID = getArguments().getInt(ID);
        }
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

        RecyclerView recyclerView = view.findViewById(R.id.recipe_recycler_view);
        RecipeDao dao = AppDataBase.getDatabase(getContext()).recipeDao();
        dao.getIngredients(mID).observe(this, str -> {
            Log.d("ingredients", str);
            List<Ingredient> ingredients = IngredientConverters.fromString(str);
        });

        List<Step> steps = new ArrayList<>();
        RecipeRecyclerViewAdapter adapter = new RecipeRecyclerViewAdapter(steps, new RecipeRecyclerViewAdapter.OnRecipeItemClickListener() {
            @Override
            public void onClick(View view, Step item, int position) {

            }
        });

        recyclerView.setAdapter(adapter);
        dao.getSteps(mID).observe(this, str -> {
            steps.clear();
            steps.addAll(StepConverters.fromString(str));
            adapter.notifyDataSetChanged();
        });
    }
}
