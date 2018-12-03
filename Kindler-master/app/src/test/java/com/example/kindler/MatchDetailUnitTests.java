package com.example.kindler;

import android.arch.lifecycle.ViewModelProviders;

import org.junit.Test;

import Database.UserViewModel;
import Database.Match;

import static org.junit.Assert.assertEquals;

/**
 * Created by samanthachang on 10/29/18.
 */

public class MatchDetailUnitTests {

    private Match testMatch = new Match();


    @Test
    public void testMatchStatus() throws Exception {
        assertEquals(testMatch.getMatchStatus(), false);
        testMatch.setMatchStatus(true);
        assertEquals(testMatch.getMatchStatus(), true);
    }

    @Test
    public void testSetMatchId() throws Exception {
        testMatch.setMatchId(123);
        assertEquals(testMatch.getMatchId(), 123);
    }

    @Test
    public void testSetMatchOwner() throws Exception {
        testMatch.setMatchOwner(55688);
        assertEquals(testMatch.getMatchOwner(), 55688);
    }

    @Test
    public void testSetMatchBookId() throws Exception {
        testMatch.setMatchBookId(345);
        assertEquals(testMatch.getMatchBookId(), 345);
    }

    @Test
    public void testSetMatchWisher() throws Exception {
        testMatch.setMatchWisher(555);
        assertEquals(testMatch.getMatchWisher(), 555);
    }

}
