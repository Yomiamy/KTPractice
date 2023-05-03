package com.example.myapplication


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.ktpractice.flow.main.view.MainActivity
import org.junit.Rule
import org.junit.Test
import com.ktpractice.R
import java.util.EnumSet.allOf


class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun testShowMainActivity() {
        onView(withText("Dynamo")).perform(ViewActions.click())
        isCompletelyDisplayed()
    }
}