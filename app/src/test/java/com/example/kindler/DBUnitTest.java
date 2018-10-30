package com.example.kindler;

import org.junit.Test;

import java.util.ArrayList;

import Database.User;

import static org.junit.Assert.*;

public class DBUnitTest {

    @Test
    public void add_owned_book_test() throws Exception{
        //create input and output array
        ArrayList<Integer> input = new ArrayList<Integer>();
        ArrayList<Integer> expected = new ArrayList<Integer>();
        for(int i=0 ; i < 10; i++){
            input.add(i*2);
            expected.add(i*2);
        }

        //set up user
        User test = new User("user1","password1");
        test.setOwnedList(input);

        //create number to insert
        Integer num = 3;

        //add number to output array
        expected.add(num);

        //insert number to user set owned list
        test.addOwnedList(num);

        //check that number was added correctly
        assertArrayEquals(expected.toArray(),test.getOwnedList().toArray());
    }

    @Test
    public void remove_owned_book_test() throws Exception{
        //create input and output array
        ArrayList<Integer> input = new ArrayList<Integer>();
        ArrayList<Integer> expected = new ArrayList<Integer>();
        for(int i=0 ; i < 10; i++){
            input.add(i);
            expected.add(i);
        }

        //set up user
        User test = new User("user1","password1");
        test.setOwnedList(input);

        //create index to remove
        Integer num = 3;

        //remove index from output array
        expected.remove(num);

        //remove index from user set owned list
        test.removeOwnedList(num);

        //check that index was removed correctly
        assertArrayEquals(expected.toArray(),test.getOwnedList().toArray());
    }

    @Test
    public void add_wish_book_test() throws Exception{
        //create input and output array
        ArrayList<Integer> input = new ArrayList<Integer>();
        ArrayList<Integer> expected = new ArrayList<Integer>();
        for(int i=0 ; i < 10; i++){
            input.add(i*2);
            expected.add(i*2);
        }

        //set up user
        User test = new User("user1","password1");
        test.setWishList(input);

        //create number to insert
        Integer num = 3;

        //add number to output array
        expected.add(num);

        //insert number to user set owned list
        test.addWishList(num);

        //check that number was added correctly
        assertArrayEquals(expected.toArray(),test.getWishList().toArray());
    }

    @Test
    public void remove_wish_book_test() throws Exception{
        //create input and output array
        ArrayList<Integer> input = new ArrayList<Integer>();
        ArrayList<Integer> expected = new ArrayList<Integer>();
        for(int i=0 ; i < 10; i++){
            input.add(i);
            expected.add(i);
        }

        //set up user
        User test = new User("user1","password1");
        test.setWishList(input);

        //create index to remove
        Integer num = 3;

        //remove index from output array
        expected.remove(num);

        //remove index from user set owned list
        test.removeWishList(num);

        //check that index was removed correctly
        assertArrayEquals(expected.toArray(),test.getWishList().toArray());
    }

    @Test
    public void add_owned_user_test() throws Exception{
        //create input and output array
        ArrayList<Integer> input = new ArrayList<Integer>();
        ArrayList<Integer> expected = new ArrayList<Integer>();
        for(int i=0 ; i < 10; i++){
            input.add(i*2);
            expected.add(i*2);
        }

        //set up book
        Database.Book test = new Database.Book("book1","pic1",input,input);

        //create number to insert
        Integer num = 3;

        //add number to output array
        expected.add(num);

        //insert number to user set owned list
        test.addOwnedUser(num);

        //check that number was added correctly
        assertArrayEquals(expected.toArray(),test.getOwnedUser().toArray());
    }

    @Test
    public void add_wish_user_test() throws Exception{
        //create input and output array
        ArrayList<Integer> input = new ArrayList<Integer>();
        ArrayList<Integer> expected = new ArrayList<Integer>();
        for(int i=0 ; i < 10; i++){
            input.add(i);
            expected.add(i);
        }

        //set up book
        Database.Book test = new Database.Book("book1","pic1",input,input);

        //create number to insert
        Integer num = 3;

        //add num to expected
        expected.add(num);

        //insert into wish user
        test.addWishUser(num);

        //check that number was added correctly
        assertArrayEquals(expected.toArray(),test.getWishUser().toArray());
    }

}
