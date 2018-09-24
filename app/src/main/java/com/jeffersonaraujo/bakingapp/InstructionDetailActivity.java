package com.jeffersonaraujo.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.jeffersonaraujo.bakingapp.helper.StepJsonHelper;

import org.json.JSONException;

public class InstructionDetailActivity extends AppCompatActivity
        implements InstructionDetailFragment.OnInstructionDetailInteractionListener {

    private Boolean mHasNext;
    private Boolean mHasPrevious;

    private StepJsonHelper mStepHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            mStepHelper = new StepJsonHelper(getIntent().getStringExtra(InstructionsActivity.EXTRA_JSON_STEP));
            setTitle(mStepHelper.getShortDescription());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(savedInstanceState == null){

            InstructionDetailFragment instructionDetailFragment = InstructionDetailFragment
                    .newInstance(getIntent().getStringExtra(InstructionsActivity.EXTRA_JSON_STEP));

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.instruction_detail_container, instructionDetailFragment)
                    .commit();
        }

        mHasNext = getIntent().getBooleanExtra(InstructionsActivity.EXTRA_HAS_NEXT, Boolean.FALSE);
        mHasPrevious = getIntent().getBooleanExtra(InstructionsActivity.EXTRA_HAS_PREVIOUS, Boolean.FALSE);
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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
