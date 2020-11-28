package org.codegrinders.treasure_hunter_mobile;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class ActivityPuzzleTest {

    @Rule
    public ActivityTestRule<ActivityStart> mActivityTestRule = new ActivityTestRule<>(ActivityStart.class);

    @Test
    public void activityPuzzleTest() {
        ViewInteraction materialButton = onView(allOf(withId(R.id.bt_continue), withText("puzzles"), childAtPosition(allOf(withId(R.id.cardViewPuzzle), childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 3)), 0), isDisplayed()));
        materialButton.perform(click());

        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.et_answer), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 4), isDisplayed()));
        appCompatEditText.perform(replaceText("10"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(allOf(withId(R.id.bt_continue), withText("continue"), childAtPosition(allOf(withId(R.id.cardView3), childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 2)), 0), isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.et_answer), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 4), isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction materialButton3 = onView(allOf(withId(R.id.bt_leaderboard), withText("LEADERBOARD"), childAtPosition(allOf(withId(R.id.cardViewLeaderboard), childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 1)), 0), isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textView = onView(allOf(withId(R.id.tv_question), withText("Does the donkey fly? (yes)"), withParent(withParent(withId(android.R.id.content))), isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction button = onView(allOf(withId(R.id.bt_continue), withText("CONTINUE"), withParent(allOf(withId(R.id.cardView3), withParent(IsInstanceOf.instanceOf(android.view.ViewGroup.class)))), isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(allOf(withId(R.id.bt_leaderboard), withText("LEADERBOARD"), withParent(allOf(withId(R.id.cardViewLeaderboard), withParent(IsInstanceOf.instanceOf(android.view.ViewGroup.class)))), isDisplayed()));
        button2.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent) && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
