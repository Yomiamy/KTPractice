package com.ktpractice.utils

import io.mockk.MockK
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.AdditionalMatchers.not
import org.mockito.Matchers
import org.mockito.Mockito
import org.mockito.internal.stubbing.answers.ThrowsException

class AuthTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun validLogin1() {
        val result1 = Utils.validLogin("123456", "12345678")
        assertEquals(true, result1)

        val result2 = Utils.validLogin("12345", "12345678")
        assertEquals(false, result2)

        val result3 = Utils.validLogin("123456", "1234567")
        assertEquals(false, result3)
    }

    @Test
    fun validLogin2() {
        val loginService = Mockito.mock(ILoginService::class.java)
        val authManager = AuthManager(loginService)

        // Arrange
        Mockito.`when`(loginService.login("123456", "12345678")).thenReturn(true)

        // Act
        val result = authManager.login("123456", "12345678")

        // Asset
//        Mockito.verify(loginService).login("123456", "1234567")
        assertEquals(true, result)
    }

    @Test
    fun validLogin3() {
        val loginService = mockk<ILoginService>()
        val authManager = AuthManager(loginService)

        every { loginService.login(any(), any()) } returns true

        val result = authManager.login("123456", "12345678")

//        verify { loginService.login("12345ˊ", "12345678") }
        assertEquals(true, result)
    }
}