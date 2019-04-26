package com.hyunyong.myapplication;

import android.content.Intent;

import com.hyunyong.myapplication.view.IngredientWidget;
import com.hyunyong.myapplication.view.MainActivity;
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
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    @JvmField
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, true, false);

    @Before
    public void setup() {
        OkHttpClient client = new OkHttpClient();
        IdlingResource resource = OkHttp3IdlingResource.create("Okhttp", client);
        Espresso.registerIdlingResources(resource);
    }

    @Test
    public void testBackingMenu() {

        activityRule.launchActivity(null);

        onView(withId(R.id.backing_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        pressBack();

        onView(withId(R.id.backing_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));


        pressBack();

        onView(withId(R.id.backing_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        pressBack();

        onView(withId(R.id.backing_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));



    }

    @Test
    public void testBackingMenuFromWidget() {
        Intent intent = new Intent();
        intent.putExtra(IngredientWidget.INGREDIENTS, true);
        activityRule.launchActivity(intent);
        onView(withId(R.id.backing_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }
}
