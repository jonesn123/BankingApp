package com.hyunyong.myapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hyunyong.myapplication.R;
import com.hyunyong.myapplication.data.Recipe;
import com.hyunyong.myapplication.db.AppDataBase;
import com.hyunyong.myapplication.db.dao.RecipeDao;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

public class BankingFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        RecipeDao recipeDao = AppDataBase.getDatabase(getContext()).recipeDao();

        List<Recipe> recipes = new ArrayList<>();
        BankingRecyclerViewAdapter adapter = new BankingRecyclerViewAdapter(recipes, (view1, item, position) -> Toast.makeText(view1.getContext(), "click :" + item.getName(), Toast.LENGTH_SHORT).show());
        recipeDao.getRecipes().observe(this, items -> {
            recipes.addAll(items);
            adapter.notifyDataSetChanged();
        });

        recyclerView.setAdapter(adapter);
    }
}
