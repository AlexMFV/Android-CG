package com.example.colorpicker;

import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class SignUpTest {

    @Rule
    public ActivityTestRule<SignUp> SUTestRule = new ActivityTestRule<SignUp>(SignUp.class);


    private String usrName = "test1@test.com";
    private String password = "Test2020";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void TestUserInputScenario()
    {
        //input some text into the edit text
        Espresso.onView(withId(R.id.userName2)).perform(typeText(usrName));
        //close soft keyboard
        Espresso.closeSoftKeyboard();
        //input some text into the edit text
        Espresso.onView(withId(R.id.userPassword2)).perform(typeText(password));
        //close soft keyboard
        Espresso.closeSoftKeyboard();
        //input some text into the edit text
        Espresso.onView(withId(R.id.userPassword3)).perform(typeText(password));
        //close soft keyboard
        Espresso.closeSoftKeyboard();
        //perform button click
        Espresso.onView(withId(R.id.btnRegister)).perform(click());
        //check result
    }
    
    @After
    public void tearDown() throws Exception {
    }
}