package com.erservice.app

import com.erservice.app.api.AuthService
import com.erservice.app.api.AuthServiceImpl
import com.erservice.app.api.PatientService
import com.erservice.app.api.PatientServiceImpl
import com.erservice.app.api.TokenManager
import com.erservice.app.api.apiBaseUrl
import com.erservice.app.api.createHttpClient

class AppContainer {
    private val tokenManager = TokenManager()
    private val httpClient = createHttpClient(apiBaseUrl, tokenManager)

    val authService: AuthService = AuthServiceImpl(httpClient)
    val patientService: PatientService = PatientServiceImpl(httpClient)

    fun setToken(token: String) {
        tokenManager.token = token
    }
}