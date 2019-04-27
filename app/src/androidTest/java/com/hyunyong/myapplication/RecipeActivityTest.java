package com.hyunyong.myapplication;

import android.content.Intent;

import com.hyunyong.myapplication.view.RecipeActivity;
import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import kotlin.jvm.JvmField;
import okhttp3.OkHttpClient;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {
    @Rule
    @JvmField
    public ActivityTestRule<RecipeActivity> activityRule = new ActivityTestRule<>(RecipeActivity.class, true, false);

    @Before
    public void setup() {
        OkHttpClient client = new OkHttpClient();
        IdlingResource resource = OkHttp3IdlingResource.create("Okhttp", client);
        Espresso.registerIdlingResources(resource);
    }

    @Test
    public void recipeTest() {
        Intent intent = new Intent();
        intent.putExtra("id", 0);
        activityRule.launchActivity(intent);

        onView(withId(R.id.recipe_two_pain_layout)).check(doesNotExist());


        onView(withId(R.id.recipe_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.prev_step)).check(matches(not(isEnabled())));

        onView(withId(R.id.next_step)).perform(click());

        onView(withId(R.id.prev_step)).check(matches(isEnabled()));

    }
}
