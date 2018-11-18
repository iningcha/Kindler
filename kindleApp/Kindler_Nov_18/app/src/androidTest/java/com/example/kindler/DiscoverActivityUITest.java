package com.example.kindler;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by samanthachang on 10/29/18.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
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