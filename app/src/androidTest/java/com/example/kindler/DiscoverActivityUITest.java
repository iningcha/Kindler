package com.example.kindler;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by samanthachang on 10/29/18.
 */
public class DiscoverActivityUITest {

    @Rule
    public ActivityTestRule<DiscoverActivity> mActivityRule =
            new ActivityTestRule<>(DiscoverActivity.class);

    @Test
    public void testUIFunction(){
        onView(withId(R.id.activity_main_progress_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.activity_main_card_stack_view)).check(matches(isDisplayed()));
    }

}