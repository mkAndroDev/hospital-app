package com.erservice.app.api

import android.util.Log
import io.ktor.client.plugins.logging.Logger

actual val platformLogger: Logger = object : Logger {
    private val TAG = "HttpClient"
    override fun log(message: String) {
        Log.v(TAG, message)
    }
}