package com.erservice.app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erservice.app.api.AuthService
import com.erservice.app.api.dto.LoginRequest
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(authService: AuthService, onLoginSuccess: (String) -> Unit) {
    var username by remember { mutableStateOf("admin") }
    var password by remember { mutableStateOf("admin123") }
    val coroutineScope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            coroutineScope.launch {
                try {
                    val response = authService.login(LoginRequest(username, password))
                    onLoginSuccess(response.token)
                } catch (e: Exception) {
                    errorMessage = "Login failed: ${e.message}"
                    println(e)
                }
            }
        }) {
            Text("Login")
        }
        errorMessage?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(it)
        }
    }
}