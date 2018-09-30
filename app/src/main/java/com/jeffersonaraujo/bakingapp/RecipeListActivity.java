package com.jeffersonaraujo.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jeffersonaraujo.bakingapp.database.AppDatabase;
import com.jeffersonaraujo.bakingapp.database.RecipeEntry;
import com.jeffersonaraujo.bakingapp.util.Util;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListActivity extends AppCompatActivity implements CardRecipeListAdapter.RecipeOnclickHandler{

    private ArrayList<String> mRecipes;

    private static String SAVED_DATA = "SAVED_DATA";
    public static final String EXTRA_JSON_RECIPE = "json_recipe";

    private boolean fromWidget = false;

    private Boolean isTablet = false;

    @BindView(R.id.recycler_recipes)
    RecyclerView mRecyclerRecipes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        ButterKnife.bind(this);

        isTablet = getResources().getBoolean(R.bool.isTablet);

        this.loadRecipes(savedInstanceState);

        this.configRecycler();
        this.openInstructionsIfFromWidget();
    }

    private void openInstructionsIfFromWidget(){
        if(getIntent() != null
                && getIntent().getStringExtra(EXTRA_JSON_RECIPE) != null){
            this.openRecipe(getIntent().getStringExtra(EXTRA_JSON_RECIPE));
        }
    }

    private void configRecycler(){

        RecyclerView.LayoutManager lm = null;

        if(isTablet){
             lm = new GridLayoutManager(this, 4);
        }else{
            lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }

        mRecyclerRecipes.setLayoutManager(lm);

        CardRecipeListAdapter mAdapter = new CardRecipeListAdapter(this, this);
        mRecyclerRecipes.setAdapter(mAdapter);

        mAdapter.setRecipesList(mRecipes);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putSerializable(SAVED_DATA, mRecipes);

        super.onSaveInstanceState(outState);
    }

    private void loadRecipes(Bundle savedInstanceState){

        try {
            if(savedInstanceState == null){
                InputStream is = getResources().openRawResource(R.raw.baking);

                JSONArray mData = new JSONArray(Util.inputStreamToString(is));

                mRecipes = new ArrayList<>();
                for(int i = 0; i < mData.length(); i++){
                    mRecipes.add(mData.get(i).toString());
                }

            }else{
                mRecipes =  (ArrayList<String>) savedInstanceState.getSerializable(SAVED_DATA);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCardClick(String json) {
        openRecipe(json);
    }

    private void openRecipe(String json){
        Intent intent = new Intent(this, InstructionsActivity.class);
        intent.putExtra(InstructionsActivity.EXTRA_JSON_RECIPE, json);

        startActivity(intent);
    }
}
