package com.jeffersonaraujo.bakingapp;


import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.content.res.Resources;

import com.jeffersonaraujo.bakingapp.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest{


    @Rule
    public IntentsTestRule<RecipeListActivity> activityTestRule =
            new IntentsTestRule<>(RecipeListActivity.class);

    @Test
    public void clickOnRecyclerViewItem_testInstructionDetailActivityIntent() throws JSONException {

        int position = 0;

        Resources resources = InstrumentationRegistry.getTargetContext().getResources();
        JSONArray json = new JSONArray(Util.inputStreamToString(resources.openRawResource((R.raw.baking))));

        Espresso.onView(ViewMatchers.withId(R.id.recycler_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position, ViewActions.click()));

        Intents.intended(
                IntentMatchers.hasExtra(InstructionsActivity.EXTRA_JSON_RECIPE, json.get(position).toString())
        );
    }

}
