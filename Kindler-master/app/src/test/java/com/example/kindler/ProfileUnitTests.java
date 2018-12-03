package com.example.kindler;

import org.junit.Test;

import Database.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by samanthachang on 10/29/18.
 */

public class ProfileUnitTests {

    private Database.Profile testProfile = new Database.Profile();


    @Test
    public void testProfileName() throws Exception {
        assertEquals(testProfile.getProfileName(), "");
        testProfile.setProfileName("testerName");
        assertEquals(testProfile.getProfileName(), "testerName");
    }

    @Test
    public void testProfileBiography() throws Exception {
        assertEquals(testProfile.getProfileBiography(), "");
        testProfile.setProfileBiography("some text");
        assertEquals(testProfile.getProfileBiography(), "some text");
    }

    @Test
    public void testProfilePicture() throws Exception {
        assertEquals(testProfile.getProfilePicture(), "");
        testProfile.setProfilePicture("examplePic");
        assertEquals(testProfile.getProfilePicture(), "examplePic");
    }

}
