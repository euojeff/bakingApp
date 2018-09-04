package com.jeffersonaraujo.bakingapp.helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class JsonHelper{

    private JSONObject mJson;

    public JsonHelper(String json) throws JSONException {
        mJson = new JSONObject(json);
    }

    public JSONObject getJson() {
        return mJson;
    }

    public String getString(String key){

        String atr = "";

        try {
            atr = mJson.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return atr;
    }

    @Override
    public String toString() {
        return mJson.toString();
    }
}
