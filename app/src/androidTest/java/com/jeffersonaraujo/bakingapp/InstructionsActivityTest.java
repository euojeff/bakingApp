package com.jeffersonaraujo.bakingapp;


import android.content.Intent;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jeffersonaraujo.bakingapp.helper.RecipeJsonHelper;
import com.jeffersonaraujo.bakingapp.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class InstructionsActivityTest {


    @Rule
    public ActivityTestRule<InstructionsActivity> activityTestRule =
            new ActivityTestRule(InstructionsActivity.class){
                @Override
                protected Intent getActivityIntent() {

                    Intent intent = new Intent();
                    Resources resources = InstrumentationRegistry.getTargetContext().getResources();
                    JSONArray json = null;
                    try {
                        json = new JSONArray(Util.inputStreamToString(resources.openRawResource((R.raw.baking))));
                        intent = new Intent();
                        intent.putExtra(InstructionsActivity.EXTRA_JSON_RECIPE, json.get(0).toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return intent;
                }
            };

    @Test
    public void clickOnInstruction_checkInstructionDetailDescription() throws JSONException{

        int position = 0;

        Resources resources = InstrumentationRegistry.getTargetContext().getResources();
        RecipeJsonHelper helper = new RecipeJsonHelper(
                new JSONArray(Util.inputStreamToString(resources.openRawResource((R.raw.baking)))).get(position).toString());

        Espresso.onView(ViewMatchers.withId(R.id.instructions_recycle))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position, ViewActions.click()));

        Espresso.onView(ViewMatchers.withId(R.id.instruction_detail_tv))
                .check(ViewAssertions.matches(ViewMatchers.withText(helper.getSteps().get(position).getDescription())));

    }
}
