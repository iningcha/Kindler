package com.example.kindler;

import android.support.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by samanthachang on 10/29/18.
 */
public class ProfileUITest {

    @Rule
    public ActivityTestRule<Profile> mActivityRule =
            new ActivityTestRule<>(Profile.class);

    @Test
    public void testUIFunction(){
        onView(withId(R.id.profilePicture)).check(matches(isDisplayed()));
        onView(withId(R.id.name)).check(matches(isDisplayed()));
        onView(withId(R.id.biography)).check(matches(isDisplayed()));
    }

}