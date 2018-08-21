package com.jeffersonaraujo.bakingapp.helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class RecipeHelper implements Serializable{

    private JSONObject mJson;

    public RecipeHelper(String json) throws JSONException {
        mJson = new JSONObject(json);
    }

    public String getName(){

        String name = "";

        try {
            name = mJson.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return name;
    }

}
