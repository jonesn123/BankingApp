package com.hyunyong.myapplication.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyunyong.myapplication.R;
import com.hyunyong.myapplication.data.Recipe;
import com.hyunyong.myapplication.databinding.ItemBackingContentBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class BankingRecyclerViewAdapter extends RecyclerView.Adapter<BankingRecyclerViewAdapter.ViewHolder> {

    private List<Recipe> items;
    private OnBankingItemClickListener listener;

    BankingRecyclerViewAdapter(List<Recipe> items, OnBankingItemClickListener listener) {
        this.listener = listener;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_backing_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Recipe recipe = items.get(position);
        holder.binding.setRecipe(recipe);
        holder.itemView.setOnClickListener(v -> listener.onClick(v, recipe, position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemBackingContentBinding binding;
        ViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }
    }

    interface OnBankingItemClickListener {
        void onClick(View view, Recipe item, int position);
    }
}
