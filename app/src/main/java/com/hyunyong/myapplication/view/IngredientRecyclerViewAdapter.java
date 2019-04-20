package com.hyunyong.myapplication.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyunyong.myapplication.R;
import com.hyunyong.myapplication.data.Ingredient;
import com.hyunyong.myapplication.data.Step;
import com.hyunyong.myapplication.databinding.ItemIngredientBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientRecyclerViewAdapter extends RecyclerView.Adapter<IngredientRecyclerViewAdapter.ViewHolder> {

    private List<Ingredient> items;

    IngredientRecyclerViewAdapter(List<Ingredient> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public IngredientRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        return new IngredientRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final IngredientRecyclerViewAdapter.ViewHolder holder, final int position) {
        final Ingredient ingredient = items.get(position);
        holder.binding.setIngredient(ingredient);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemIngredientBinding binding;
        ViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }
    }
}
