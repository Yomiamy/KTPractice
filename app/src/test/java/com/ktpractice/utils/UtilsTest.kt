package com.ktpractice.utils

import org.junit.Assert.*

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UtilsTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun validLogin() {
        val result1 = Utils.validLogin("123456", "12345678")
        assertEquals(true, result1)

        val result2 = Utils.validLogin("12345", "12345678")
        assertEquals(false, result2)

        val result3 = Utils.validLogin("123456", "1234567")
        assertEquals(false, result3)
    }
}