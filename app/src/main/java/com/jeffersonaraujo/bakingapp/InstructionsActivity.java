package com.jeffersonaraujo.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jeffersonaraujo.bakingapp.database.AppDatabase;
import com.jeffersonaraujo.bakingapp.database.RecipeEntry;
import com.jeffersonaraujo.bakingapp.helper.RecipeJsonHelper;
import com.jeffersonaraujo.bakingapp.helper.StepJsonHelper;

import org.json.JSONException;

import java.util.List;

public class InstructionsActivity extends AppCompatActivity
        implements InstructionsFragment.OnInstructionInteractionListener, InstructionDetailFragment.OnInstructionDetailInteractionListener {

    public static final String BUNDLE_JSON_RECIPE = "json_recipe";
    public static final String BUNDLE_JSON_STEP = "json_STEP";
    public static final String BUNDLE_HAS_PREVIOUS = "BUNDLE_HAS_PREVIOUS";
    public static final String BUNDLE_HAS_NEXT = "BUNDLE_HAS_NEXT";

    public static final String RESULT_CLICK_NEXT = "next";
    public static final String RESULT_CLICK_PREVIOUS = "previous";
    public static final String RESULT_CLICK = "RESULT_CLICK";

    private static final String SAVED_SELECTED_ITEM = "SAVED_SELECTED_ITEM";

    private List<StepJsonHelper> mStepList = null;

    private int mSelectedItem = -1;

    private Boolean isTablet = false;

    private Context mContext;
    private AppDatabase mDb;

    private void updateWidget(String json){

        new AsyncTask<RecipeEntry, Void, Void>(){
            @Override
            protected Void doInBackground(RecipeEntry... recipe) {
                mDb.recipeDao().clean();
                mDb.recipeDao().insertRecipe(recipe[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                WidgetService.startActionUpdateWidget(mContext);
            }
        }.execute(new RecipeEntry(json));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(SAVED_SELECTED_ITEM, mSelectedItem);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        mContext = this;
        mDb = AppDatabase.getInstance(this);

        isTablet = getResources().getBoolean(R.bool.isTablet);
        String recipeJson = getIntent().getStringExtra(BUNDLE_JSON_RECIPE);

        try {
            mStepList = new RecipeJsonHelper(recipeJson).getSteps();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(savedInstanceState == null){

            this.updateWidget(recipeJson);

            this.loadInstructionsFragment();

            if(isTablet){
                this.loadDetailFragment();
            }

        }else{

            mSelectedItem = savedInstanceState.getInt(SAVED_SELECTED_ITEM, -1);
        }
    }

    private void loadInstructionsFragment(){
        InstructionsFragment instructionsFragment = InstructionsFragment
                .newInstance(getIntent().getStringExtra(BUNDLE_JSON_RECIPE));

        getSupportFragmentManager().beginTransaction()
                .add(R.id.instructions_container, instructionsFragment)
                .commit();
    }

    private void loadDetailFragment(){

        if(mStepList != null
            && !mStepList.isEmpty()){

            InstructionDetailFragment instructionDetailFragment = InstructionDetailFragment
                    .newInstance(mStepList.get(0).toString());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.instruction_detail_container, instructionDetailFragment)
                    .commit();
        }
    }

    @Override
    public void onStepSelected(int i) {

        if(mStepList != null){
            if(isTablet){

                InstructionDetailFragment instructionDetailFragment = InstructionDetailFragment
                        .newInstance(mStepList.get(i).toString());

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.instruction_detail_container, instructionDetailFragment)
                        .commit();

            }else{
                Intent intent = new Intent(this, InstructionDetailActivity.class);
                intent.putExtra(InstructionsActivity.BUNDLE_JSON_STEP, mStepList.get(i).toString());
                intent.putExtra(InstructionsActivity.BUNDLE_HAS_PREVIOUS, i != 0 ? true : false);
                intent.putExtra(InstructionsActivity.BUNDLE_HAS_NEXT, i < mStepList.size() - 1 ? true : false);

                startActivityForResult(intent, 1);
            }

            mSelectedItem = i;
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

    @Override
    public void onClickPrevious() {
        // Do nothing
    }

    @Override
    public void onClickNext() {
        // Do nothing
    }
}
