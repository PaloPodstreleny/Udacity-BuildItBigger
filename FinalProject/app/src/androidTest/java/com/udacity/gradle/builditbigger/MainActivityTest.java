package com.udacity.gradle.builditbigger;
import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.gradle.builditbigger.idlingResource.SimpleIdlingResource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public IntentsTestRule<SimpleFragmentActivityTest> activityTestRule = new IntentsTestRule<>(SimpleFragmentActivityTest.class);
    private SimpleIdlingResource mIdlingResource;
    private TestMainActivityFragment mFragment;

    @Before
    public void init(){
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK,null));

        mFragment = new TestMainActivityFragment();
        mIdlingResource = new SimpleIdlingResource();
        mFragment.setIdlingResource(mIdlingResource);

        activityTestRule.getActivity().setFragment(mFragment);
        EspressoTestUtil.disableProgressBarAnimations(activityTestRule);
    }

    @Test
    public void loadingTest(){
        //Run test on emulator
        onView(withId(R.id.joke_btn)).perform(click());
        IdlingRegistry.getInstance().register(mIdlingResource);

        //Check if asyncTask returns string > 0
        final String joke = mFragment.getJoke();
        if(joke == null) {
            onView(withId(R.id.error_tv)).check(matches(isDisplayed()));
            fail("Joke is null!");
        }
        assertTrue(joke.length() > 0);

    }




    //Create fake testMainFragment class
    public static class TestMainActivityFragment extends MainActivityFragment{


    }




}
