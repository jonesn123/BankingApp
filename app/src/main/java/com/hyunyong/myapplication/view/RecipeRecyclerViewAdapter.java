package com.hyunyong.myapplication.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyunyong.myapplication.R;
import com.hyunyong.myapplication.data.Step;
import com.hyunyong.myapplication.databinding.ItemRecipeItemBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {

    private List<Step> items;
    private RecipeRecyclerViewAdapter.OnRecipeItemClickListener listener;

    RecipeRecyclerViewAdapter(List<Step> items, RecipeRecyclerViewAdapter.OnRecipeItemClickListener listener) {
        this.listener = listener;
        this.items = items;
    }

    @NonNull
    @Override
    public RecipeRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe_item, parent, false);
        return new RecipeRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeRecyclerViewAdapter.ViewHolder holder, final int position) {
        final Step step = items.get(position);
        holder.binding.setStep(step);
        holder.itemView.setOnClickListener(v -> listener.onClick(v, step, position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemRecipeItemBinding binding;
        ViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }
    }

    interface OnRecipeItemClickListener {
        void onClick(View view, Step item, int position);
    }
}
