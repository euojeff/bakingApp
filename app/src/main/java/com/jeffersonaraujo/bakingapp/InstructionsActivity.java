package com.jeffersonaraujo.bakingapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jeffersonaraujo.bakingapp.helper.RecipeJsonHelper;
import com.jeffersonaraujo.bakingapp.helper.StepJsonHelper;

import org.json.JSONException;

import java.util.List;

public class InstructionsActivity extends AppCompatActivity
        implements InstructionsFragment.OnInstructionInteractionListener{

    public static final String BUNDLE_JSON_RECIPE = "json_recipe";
    public static final String BUNDLE_HAS_PREVIOUS = "BUNDLE_HAS_PREVIOUS";
    public static final String BUNDLE_HAS_NEXT = "BUNDLE_HAS_NEXT";

    public static final String RESULT_CLICK_NEXT = "next";
    public static final String RESULT_CLICK_PREVIOUS = "previous";
    public static final String RESULT_CLICK = "RESULT_CLICK";

    private List<StepJsonHelper> mStepList = null;

    private int mSelectedItem = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);


        if(savedInstanceState == null){

            InstructionsFragment instructionsFragment = InstructionsFragment
                    .newInstance(getIntent().getStringExtra(BUNDLE_JSON_RECIPE));

            try {
                mStepList = new RecipeJsonHelper(getIntent().getStringExtra(BUNDLE_JSON_RECIPE)).getSteps();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.instructions_container, instructionsFragment)
                    .commit();

        }
    }

    @Override
    public void onStepSelected(int i) {

        if(mStepList != null){
            Intent intent = new Intent(this, InstructionDetailActivity.class);
            intent.putExtra(InstructionsActivity.BUNDLE_JSON_RECIPE, mStepList.get(i).toString());
            intent.putExtra(InstructionsActivity.BUNDLE_HAS_PREVIOUS, i != 0 ? true : false);
            intent.putExtra(InstructionsActivity.BUNDLE_HAS_NEXT, i < mStepList.size() - 1 ? true : false);

            mSelectedItem = i;

            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(RESULT_OK == resultCode){
            String RESULT = data.getStringExtra(RESULT_CLICK);
            if(RESULT_CLICK_NEXT.equals(RESULT)){

                mSelectedItem++;
                onStepSelected(mSelectedItem);

            }else if(RESULT_CLICK_PREVIOUS.equals(RESULT)){

                mSelectedItem--;
                onStepSelected(mSelectedItem);

            }
        }
    }
}
