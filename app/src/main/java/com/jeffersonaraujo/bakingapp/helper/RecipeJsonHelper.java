package com.jeffersonaraujo.bakingapp.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecipeJsonHelper extends JsonHelper{

    public RecipeJsonHelper(String json) throws JSONException {
        super(json);
    }

    public String getName(){
        return getString("name");
    }

    public ArrayList<StepJsonHelper> getSteps(){

        ArrayList<StepJsonHelper> stepsList = new ArrayList<>();

        try {
            JSONArray steps = getJson().getJSONArray("steps");

            for(int i = 0; i < steps.length(); i++){
                stepsList.add(new StepJsonHelper(steps.get(i).toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stepsList;
    }

    public ArrayList<IngredientJsonHelper> getIngredients(){

        ArrayList<IngredientJsonHelper> ingredientsList = new ArrayList<>();

        try {
            JSONArray steps = getJson().getJSONArray("ingredients");

            for(int i = 0; i < steps.length(); i++){
                ingredientsList.add(new IngredientJsonHelper(steps.get(i).toString()));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ingredientsList;
    }
}
