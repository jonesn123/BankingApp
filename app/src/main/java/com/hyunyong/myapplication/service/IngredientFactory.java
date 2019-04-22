package com.hyunyong.myapplication.service;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.hyunyong.myapplication.R;
import com.hyunyong.myapplication.data.Ingredient;
import com.hyunyong.myapplication.db.AppDataBase;
import com.hyunyong.myapplication.view.IngredientWidget;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;

public class IngredientFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<Ingredient> ingredients;
    private Context context;
    private int appWidgetId;

    public IngredientFactory(Context context, Intent intent) {
        this.context = context;
        this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        ingredients = AppDataBase.getDatabase(context).ingredientDao().getIngredients();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row =
                new RemoteViews(context.getPackageName(), R.layout.widget_item);

        Ingredient ingredient = ingredients.get(position);
        row.setTextViewText(R.id.ingredient, ingredient.getIngredient());
        row.setTextViewText(R.id.quantity, context.getString(R.string.format_ingredient, ingredient.getQuantity(), ingredient.getMeasure()));

        Intent intent = new Intent();
        Bundle extras = new Bundle();

        extras.putString(IngredientWidget.INGREDIENTS, ingredient.getIngredient());
        extras.putInt(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.putExtras(extras);

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
