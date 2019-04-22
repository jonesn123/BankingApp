package com.hyunyong.myapplication.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientFactory(this.getApplicationContext(), intent);
    }
}
