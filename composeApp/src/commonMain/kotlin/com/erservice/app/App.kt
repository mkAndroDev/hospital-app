package com.erservice.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.erservice.app.api.Patient
import com.erservice.app.screens.AddPatientScreen
import com.erservice.app.screens.LoginScreen
import com.erservice.app.screens.PatientDetailsScreen
import com.erservice.app.screens.PatientListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

sealed class Screen {
    data object PatientList : Screen()
    data class PatientDetails(val patient: Patient) : Screen()
    data object AddPatient : Screen()
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        val appContainer = remember { AppContainer() }
        var isLoggedIn by remember { mutableStateOf(false) }
        val navStack = remember { mutableStateListOf<Screen>(Screen.PatientList) }

        val currentScreen = navStack.last()

        if (isLoggedIn) {
            when (val screen = currentScreen) {
                is Screen.PatientList -> {
                    PatientListScreen(
                        patientService = appContainer.patientService,
                        onPatientClick = { navStack.add(Screen.PatientDetails(it)) },
                        onAddPatientClick = { navStack.add(Screen.AddPatient) }
                    )
                }

                is Screen.PatientDetails -> {
                    PatientDetailsScreen(screen.patient, appContainer.patientService) {
                        navStack.removeLast()
                    }
                }

                is Screen.AddPatient -> {
                    AddPatientScreen(appContainer.patientService) {
                        navStack.removeLast()
                    }
                }
            }
        } else {
            LoginScreen(appContainer.authService) {
                token ->
                appContainer.setToken(token)
                isLoggedIn = true
            }
        }
    }
}