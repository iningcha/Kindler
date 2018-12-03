package com.example.kindler;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchInstrumentedTest {

    @Rule
    public ActivityTestRule<Search> mSearchRule = new ActivityTestRule<>(Search.class);

    @Test
    public void ensureLayoutAppears() {
        onView(withId(R.id.searchText)).check(matches(isDisplayed()));
        onView(withId(R.id.searchButton)).check(matches(isDisplayed()));
        onView(withId(R.id.searchResultsList)).check(matches(isDisplayed()));
    }

    @Test
    public void ensureTextChangesWork() {
        // Type text and then press the button.
        onView(withId(R.id.searchText))
                .perform(typeText("Harry Potter"), closeSoftKeyboard())
                .check(matches(withText("Harry Potter")));
    }

}