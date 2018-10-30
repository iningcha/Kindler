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
public class MatchDetailButton {

    @Rule
    public ActivityTestRule<MatchDetail> mActivityRule =
            new ActivityTestRule<>(MatchDetail.class);

    @Test
    public void testUIfunction() {
        onView(withId(R.id.messageButton)).check(matches(isDisplayed()));

    }
}
