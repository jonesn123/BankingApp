package com.hyunyong.myapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hyunyong.myapplication.R;
import com.hyunyong.myapplication.data.Recipe;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe(1,"asdf",null,null,12,""));

        BankingRecyclerViewAdapter adapter = new BankingRecyclerViewAdapter(recipes, new BankingRecyclerViewAdapter.OnRecipeItemClickListener() {
            @Override
            public void onClick(View view, Recipe item, int position) {
                Toast.makeText(view.getContext(), "click :" + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
