package com.jeffersonaraujo.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CardRecipeAdapter.RecipeOnclickHandler{

    private ArrayList<String> mRecipes;

    private static String SAVED_DATA = "SAVED_DATA";

    @BindView(R.id.recycler_recipes)
    RecyclerView mRecyclerRecipes;
    CardRecipeAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        this.loadRecipes(savedInstanceState);

        this.configRecycler();


    }

    private void configRecycler(){

        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerRecipes.setLayoutManager(lm);

        mAdapter = new CardRecipeAdapter(this, this);
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

                JSONArray mData = new JSONArray(this.inputStreamToString(is));

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

    private String inputStreamToString(InputStream is ){
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    @Override
    public void onCardClick(String json) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(RecipeActivity.BUNDLE_JSON_RECIPE, json);

        startActivity(intent);
    }
}
