package com.example.kindler;

import android.content.Intent;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by samanthachang on 10/29/18.
 */

public class MainActivityUnitTests {


    @Test
    public void testSwipeLeft() throws Exception {
        MainActivity testMainActivitiy = new MainActivity();
        assertEquals(0,testMainActivitiy.getCardStackSize());
//        Intent i = new Intent(this, MainActivity.class);
//        startActivity(new Intent(presentActivity.this, NextActivity.class));
        testMainActivitiy.onCreate(null);
        assertEquals(9,testMainActivitiy.getCardStackSize());
        testMainActivitiy.swipeLeft();
        assertEquals(testMainActivitiy.getCardStackSize(),8);
    }

    @Test
    public void testSwipeRight() throws Exception {
        MainActivity testMainActivitiy = new MainActivity();
        assertEquals(testMainActivitiy.getCardStackSize(),9);
        testMainActivitiy.swipeRight();
        testMainActivitiy.swipeRight();
        assertEquals(testMainActivitiy.getCardStackSize(),7);
    }

}
