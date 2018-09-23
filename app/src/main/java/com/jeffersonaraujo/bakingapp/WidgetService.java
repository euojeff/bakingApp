package com.jeffersonaraujo.bakingapp;

/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.jeffersonaraujo.bakingapp.database.AppDatabase;
import com.jeffersonaraujo.bakingapp.database.RecipeEntry;
import com.jeffersonaraujo.bakingapp.helper.RecipeJsonHelper;

import org.json.JSONException;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class WidgetService extends IntentService {

    public static final String ACTION_UPDATE_WIDGET = "com.jeffersonaraujo.bakingapp.update_widget";

    private AppDatabase mDb;

    public WidgetService() {
        super("WidgetService");
    }

    public static void startActionUpdateWidget(Context context) {
        Intent intent = new Intent(context, WidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mDb = AppDatabase.getInstance(this);
    }

    /**
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_WIDGET.equals(action)) {
                handleActionUpdateWidgets();
            }
        }
    }

    private void handleActionUpdateWidgets() {

        RecipeEntry recipe = mDb.recipeDao().loadSelectedRecipe();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_text);

        try {
            BakingAppWidget.updateAppWidgets(this, appWidgetManager, appWidgetIds, new RecipeJsonHelper(recipe.getJson()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
