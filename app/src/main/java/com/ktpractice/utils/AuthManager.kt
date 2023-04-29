package com.ktpractice.utils

// TODO: For Test
interface ILoginService {
    fun login(account: String, password: String): Boolean
}

class AuthManager(private val loginService: ILoginService) {
    fun login(account: String, password: String): Boolean {
        return loginService.login(account, password)
    }
}

class LoginService : ILoginService {
    override fun login(account: String, password: String): Boolean {
        return true
    }
}