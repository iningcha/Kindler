package com.example.kindler;



import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class SignUpUnitTest {
    SignupActivity myObjectUnderTest = new SignupActivity();
    private static final String STRING1 = "Name field is valid";
    private static final String STRING2="Valid Email";
    private static final String STRING3 = "Valid Password";
    private static final String STRING4="Passwords matched";


    @Test
    public void isValidName(){
        boolean result = myObjectUnderTest.isEmptyName("sehaj");

        // ...then the result should be the expected one.

        if (result){
            assertThat("Name field is not valid", is(STRING1));


        }else{

            assertThat(STRING1, is(STRING1));
        }
    }

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

    @Test
    public void passwordMatch(){
        boolean result = myObjectUnderTest.isConfirmPass("12345","12345");

        // ...then the result should be the expected one.

        if (result){
            assertThat(STRING4, is(STRING4));
        }else{
            assertThat("Passwords not matched", is(STRING4));
        }
    }

}