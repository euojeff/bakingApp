package com.jeffersonaraujo.bakingapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class InstructionDetailActivity extends AppCompatActivity
        implements InstructionDetailFragment.OnInstructionDetailInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_detail);

        if(savedInstanceState == null){

            InstructionDetailFragment instructionDetailFragment = InstructionDetailFragment
                    .newInstance(getIntent().getStringExtra(InstructionsActivity.BUNDLE_JSON_RECIPE));

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.instruction_detail_container, instructionDetailFragment)
                    .commit();

        }
    }

    @Override
    public void onFragmentInteraction() {

    }
}
