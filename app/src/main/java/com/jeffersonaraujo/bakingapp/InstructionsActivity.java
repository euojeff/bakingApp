package com.jeffersonaraujo.bakingapp;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InstructionsActivity extends AppCompatActivity implements InstructionsFragment.OnFragmentInteractionListener{

    public static final String BUNDLE_JSON_RECIPE = "json_recipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);


        if(savedInstanceState == null){

            InstructionsFragment instructionsFragment = InstructionsFragment
                    .newInstance(getIntent().getStringExtra(BUNDLE_JSON_RECIPE));

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.recipe_container, instructionsFragment)
                    .commit();

        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
