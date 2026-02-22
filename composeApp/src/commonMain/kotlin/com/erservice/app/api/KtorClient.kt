package com.erservice.app.api

import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(baseUrl: String, tokenManager: TokenManager) = HttpClient {
    defaultRequest {
        url(baseUrl)
    }
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    install(Logging) {
        logger = platformLogger
        level = LogLevel.ALL
    }
    install(Auth) {
        bearer {
            loadTokens {
                tokenManager.token?.let { BearerTokens(it, "") }
            }
            refreshTokens {
                tokenManager.token?.let { BearerTokens(it, "") }
            }
            sendWithoutRequest { true }
        }
    }
}