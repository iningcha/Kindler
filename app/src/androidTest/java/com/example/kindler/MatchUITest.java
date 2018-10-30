package com.example.kindler;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.Espresso.onData;
import static org.hamcrest.Matchers.anything;


/**
 * Created by samanthachang on 10/29/18.
 */
public class MatchUITest {

    @Rule
    public ActivityTestRule<Match> mActivityRule =
            new ActivityTestRule<>(Match.class);

    @Test
    public void testUIFunction(){
        onView(withId(R.id.matchList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.matchList)).atPosition(0).
                onChildView(withId(R.id.matchImage)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.matchList)).atPosition(0).
                onChildView(withId(R.id.matchUserId)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.matchList)).atPosition(0).
                onChildView(withId(R.id.matchBookTitle)).check(matches(isDisplayed()));

    }

}