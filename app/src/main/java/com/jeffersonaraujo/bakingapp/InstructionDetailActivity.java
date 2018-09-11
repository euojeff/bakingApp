package com.jeffersonaraujo.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class InstructionDetailActivity extends AppCompatActivity
        implements InstructionDetailFragment.OnInstructionDetailInteractionListener {

    private Boolean mHasNext;
    private Boolean mHasPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_detail);

        if(savedInstanceState == null){

            InstructionDetailFragment instructionDetailFragment = InstructionDetailFragment
                    .newInstance(getIntent().getStringExtra(InstructionsActivity.BUNDLE_JSON_RECIPE));

            mHasNext = getIntent().getBooleanExtra(InstructionsActivity.BUNDLE_HAS_NEXT, Boolean.FALSE);

            mHasPrevious = getIntent().getBooleanExtra(InstructionsActivity.BUNDLE_HAS_PREVIOUS, Boolean.FALSE);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.instruction_detail_container, instructionDetailFragment)
                    .commit();

        }
    }

    @Override
    public void onClickNext() {
        if(mHasNext){
            Intent data = new Intent();
            data.putExtra(InstructionsActivity.RESULT_CLICK, InstructionsActivity.RESULT_CLICK_NEXT);
            setResult(RESULT_OK, data);
            this.finish();
        }
    }

    @Override
    public void onClickPrevious() {
        if(mHasPrevious){
            Intent data = new Intent();
            data.putExtra(InstructionsActivity.RESULT_CLICK, InstructionsActivity.RESULT_CLICK_PREVIOUS);
            setResult(RESULT_OK, data);
            this.finish();
        }
    }
}
