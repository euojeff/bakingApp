package com.jeffersonaraujo.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jeffersonaraujo.bakingapp.database.AppDatabase;

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

    private boolean fromWidget = false;

    private Boolean isTablet = false;

    @BindView(R.id.recycler_recipes)
    RecyclerView mRecyclerRecipes;

    private AppDatabase mDb;

    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        ButterKnife.bind(this);

        mContext = this;
        mDb = AppDatabase.getInstance(this);

        isTablet = getResources().getBoolean(R.bool.isTablet);

        this.loadRecipes(savedInstanceState);

        this.configRecycler();
//        this.openInstructionsIfFromWidget();
    }

//    private void openInstructionsIfFromWidget(){
//        if(getIntent() != null
//                && getIntent().getBooleanExtra(EXTRA_FROM_WIDGET, false)){
//            mDb.recipeDao().loadSelectedRecipeLiveData().observe(this, new Observer<RecipeEntry>() {
//                @Override
//                public void onChanged(@Nullable RecipeEntry recipeEntry) {
//                    if(recipeEntry != null){
//                        onCardClick(recipeEntry.getJson());
//                    }
//                }
//            });
//        }
//    }

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
        Intent intent = new Intent(this, InstructionsActivity.class);
        intent.putExtra(InstructionsActivity.EXTRA_JSON_RECIPE, json);

        startActivity(intent);
    }
}
