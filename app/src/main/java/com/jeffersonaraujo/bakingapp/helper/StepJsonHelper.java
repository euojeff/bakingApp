package com.jeffersonaraujo.bakingapp.helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class StepJsonHelper extends JsonHelper{

    private JSONObject mJson;

    public StepJsonHelper(String json) throws JSONException {
        super(json);
    }

    public String getId(){
        return getString("id");
    }

    public String getShortDescription(){
        return getString("shortDescription");
    }

    public String getDescription(){
        return getString("description");
    }

    public String getVideoURL(){
        return getString("videoURL");
    }
}
