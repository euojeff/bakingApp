package com.jeffersonaraujo.bakingapp.helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class IngredientJsonHelper extends JsonHelper{

    public IngredientJsonHelper(String json) throws JSONException {
        super(json);
    }

    public String getFormatedString(){
        return getQuantity() + " " + getMeasure() + " " + getIngredient();
    }

    public String getQuantity(){
        return getString("quantity");
    }

    public String getMeasure(){
        return getString("measure");
    }

    public String getIngredient(){
        return getString("ingredient");
    }
}
