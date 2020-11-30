package com.example.cps731project;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;

import androidx.test.espresso.Espresso;
import androidx.test.filters.SmallTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

import static org.hamcrest.MatcherAssert.assertThat; import static org.hamcrest.Matchers.*;
import junit.framework.TestCase;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@SmallTest
// @RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<LoginPage> mActivityTestRule = new ActivityTestRule<>(LoginPage.class);
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.cps731project", appContext.getPackageName());
    }
    @Test
    public void activity_changes() throws InterruptedException {
        onView(withId(R.id.UsernameET)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.PasswordET)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.RegisterBtn)).perform(click());
        Thread.sleep(1500);
        onView(withId(R.id.findRestaurantsBtn)).check(matches(isDisplayed()));
    }

    @Test
    public void back_closesActivity() throws InterruptedException {
        activity_changes();
        Espresso.pressBack();
        Thread.sleep(1500);
        onView(withId(R.id.UsernameET)).check(matches(isDisplayed()));
    }

    @Test
    public void badLogin_check() throws InterruptedException {
        onView(withId(R.id.UsernameET)).perform(typeText("dwbwuandijsabsduagb"), closeSoftKeyboard());
        onView(withId(R.id.PasswordET)).perform(typeText("23123871298"), closeSoftKeyboard());
        onView(withId(R.id.RegisterBtn)).perform(click());
        Thread.sleep(1500);
        onView(withId(R.id.registerResult)).check(matches(withText("Username and/or Password is Incorrect")));
    }

    @Test
    public void register_check() throws InterruptedException {
        onView(withId(R.id.RegisterTxtV)).perform(click());
        onView(withId(R.id.UsernameET)).perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.PasswordET)).perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.RegisterBtn)).perform(click());
        Thread.sleep(1500);
        onView(withId(R.id.UsernameET)).perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.PasswordET)).perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.RegisterBtn)).perform(click());
        Thread.sleep(1500);
        onView(withId(R.id.findRestaurantsBtn)).check(matches(isDisplayed()));
    }

}