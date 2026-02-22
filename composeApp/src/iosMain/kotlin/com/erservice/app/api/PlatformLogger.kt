package com.erservice.app.api

import io.ktor.client.plugins.logging.Logger
import platform.Foundation.NSLog

actual val platformLogger: Logger = object : Logger {
    override fun log(message: String) {
        NSLog(message)
    }
}