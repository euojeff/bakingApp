package com.jeffersonaraujo.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RecipeActivity extends AppCompatActivity {

    public static final String BUNDLE_JSON_RECIPE = "json_recipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
    }
}
