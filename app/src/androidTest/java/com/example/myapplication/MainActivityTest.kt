package com.example.myapplication


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.ktpractice.flow.main.view.MainActivity
import org.junit.Rule
import org.junit.Test
import com.ktpractice.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.core.Is.`is`


class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun testTabClick() {
        onView(withText("Dynamo")).perform(ViewActions.click())

        onView(withText("Elastic")).perform(ViewActions.click())

        onView(withText("Rangers")).perform(ViewActions.click())
    }

    @Test
    fun scrollRecycleView() {
        onView(allOf(withParent(withId(R.id.vp_view_pager)), withClassName(`is`("androidx.recyclerview.widget.RecyclerView")))).perform(ViewActions.swipeUp())
    }
}