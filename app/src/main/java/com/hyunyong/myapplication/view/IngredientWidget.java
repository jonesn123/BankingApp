package com.hyunyong.myapplication.view;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.hyunyong.myapplication.R;
import com.hyunyong.myapplication.service.WidgetService;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidget extends AppWidgetProvider {

    public static String INGREDIENTS=
            "com.hyunyong.myapplication.view.INGREDIENTS";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);


        Intent widgetService = new Intent(context, WidgetService.class);
        widgetService.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        widgetService.setData(Uri.parse(widgetService.toUri(Intent.URI_INTENT_SCHEME)));

        views.setRemoteAdapter(R.id.appwidget_ingredient_list_view, widgetService);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

