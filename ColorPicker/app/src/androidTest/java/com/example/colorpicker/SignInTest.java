package com.example.colorpicker;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class SignInTest {

    @Rule
    public ActivityTestRule<SignIn> SITestRule = new ActivityTestRule<SignIn>(SignIn.class);

    private String usrName = "test@test.com";
    private String password = "Test20";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void TestUserInputScenario()
    {
        //input some text into the edit text
        Espresso.onView(withId(R.id.userName)).perform(typeText(usrName));
        //close soft keyboard
        Espresso.closeSoftKeyboard();
        //input some text into the edit text
        Espresso.onView(withId(R.id.userPassword)).perform(typeText(password));
        //close soft keyboard
        Espresso.closeSoftKeyboard();
        //click button
        Espresso.onView(withId(R.id.btnSignin)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
    }
}