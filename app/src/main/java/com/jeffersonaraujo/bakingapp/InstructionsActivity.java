package com.jeffersonaraujo.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InstructionsActivity extends AppCompatActivity
        implements InstructionsFragment.OnInstructionInteractionListener{

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
                    .add(R.id.instructions_container, instructionsFragment)
                    .commit();

        }
    }

    @Override
    public void onInstructionSelected(String json) {
        Intent intent = new Intent(this, InstructionDetailActivity.class);
        intent.putExtra(InstructionsActivity.BUNDLE_JSON_RECIPE, json);
        startActivity(intent);
    }
}
