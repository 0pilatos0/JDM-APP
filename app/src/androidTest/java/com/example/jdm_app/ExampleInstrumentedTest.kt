package com.example.jdm_app

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.jdm_app.activity.RegistrationActivity
import org.junit.runners.MethodSorters;
import org.junit.Assert.*

import org.junit.FixMethodOrder;
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.filters.LargeTest
import com.example.jdm_app.activity.MainActivity
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
@LargeTest
class EspressoRegister {

    @get:Rule
    val activityRule = ActivityScenarioRule(RegistrationActivity::class.java)

    @Test
    fun RegisterPageIsWorking() {
        onView(withId(R.id.register_username_field)).perform(ViewActions.typeText("EspressoTest"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.register_address_field)).perform(ViewActions.typeText("Espresso Street 5"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.register_number_field)).perform(ViewActions.typeText("0612345678"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.register_button)).perform(ViewActions.click())
    }
}

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
@LargeTest
class EspressoActions {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun navigationBarIsDisplayedAndWorking() {
        onView(withId(R.id.bottom_navigation_view)).check(matches(isDisplayed()))
        onView(withId(R.id.action_reservation)).perform(ViewActions.click())
        onView(withId(R.id.bottom_navigation_view)).check(matches(isDisplayed()))
        onView(withId(R.id.action_profile)).perform(ViewActions.click())
        onView(withId(R.id.bottom_navigation_view)).check(matches(isDisplayed()))
        onView(withId(R.id.action_cars)).perform(ViewActions.click())
    }

    @Test
    fun carCreateIsWorking() {
        onView(withId(R.id.my_cars_button)).perform(ViewActions.click())
        onView(withText("New Car")).check(matches(isDisplayed()))
        onView(withText("New Car")).perform(ViewActions.click())
        onView(withId(R.id.action_save)).perform(ViewActions.click())
        onView(withId(R.id.button_back)).perform(ViewActions.click())
    }

    @Test
    fun rentCarIsWorking() {
        onView(withText("Chevrolet")).perform(ViewActions.click())
        onView(withText("Rent")).perform(ViewActions.click())
        onView(withId(R.id.edit_text_terms_conditions)).perform(ViewActions.typeText("These are the terms and conditions!"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.edit_text_postal_code)).perform(ViewActions.typeText("1234AB"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.edit_text_house_number)).perform(ViewActions.typeText("1225"))
        onView(withId(R.id.action_save)).perform(ViewActions.click())
    }
}