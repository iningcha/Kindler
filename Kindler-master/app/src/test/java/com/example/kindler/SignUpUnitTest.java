package com.example.kindler;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)

public class SignUpUnitTest {

    private static final String success = "Login was successful";
    private static final String failure="Login was unsuccessful";


    @Mock
    Context mMockContext;

    @Test
    public void readStringFromContext_LocalizedString() {

        SignupActivity myObjectUnderTest = new SignupActivity();

        // ...when the string is returned from the object under test...
        boolean result = myObjectUnderTest.validate();

        // ...then the result should be the expected one.

        if (result){
            assertThat("Success", is(success));
        }else{
            assertThat("Failure", is(failure));
        }

        //
    }

}