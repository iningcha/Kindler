package com.example.kindler;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;



public class LoginUnitTest {

    private static final String STRING2="Valid Email";
    private static final String STRING3 = "Valid Password";

    LoginActivity myObjectUnderTest = new LoginActivity();

    @Test
    public void checkEmail(){
        boolean result = myObjectUnderTest.isEmail("kand@usc.edu");

        // ...then the result should be the expected one.

        if (result){
         assertThat(STRING2, is(STRING2));
        }else{
           assertThat("Empty or Invalid Email Address", is(STRING2));
        }
    }

    @Test
    public void checkPassword(){
        boolean result = myObjectUnderTest.isPassword("12345");

        // ...then the result should be the expected one.

        if (result){
            assertThat(STRING3, is(STRING3));
        }else{
         assertThat("Invalid Password", is(STRING3));
        }
    }
}