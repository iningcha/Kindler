package com.example.kindler;

/**
 * Created by samanthachang on 10/29/18.
 */

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.filters.LargeTest;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import org.junit.Test;
import org.junit.Rule;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MatchDetailUITest {

    @Rule
    public ActivityTestRule<MatchDetail> mActivityRule =
            new ActivityTestRule<>(MatchDetail.class);

    @Test
    public void testUIfunction() {
        onView(withId(R.id.matchDetail_userName)).check(matches(isDisplayed()));
        onView(withId(R.id.matchDetail_bookName)).check(matches(isDisplayed()));
        onView(withId(R.id.matchDetail_profilePicture)).check(matches(isDisplayed()));
    }
}