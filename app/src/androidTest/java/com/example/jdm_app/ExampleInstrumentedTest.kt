package com.example.jdm_app

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.jdm_app.activity.MainActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule




@RunWith(AndroidJUnit4)
@LargeTest
class HelloWorldEspressoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    class ActivityScenarioRule(java: Class<MainActivity>) {

    }

    @Test fun listGoesOverTheFold() {
        onView(withText("Delete")).check(matches(isDisplayed()))
    }
}