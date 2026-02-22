package com.erservice.app.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.erservice.app.api.Patient
import com.erservice.app.api.PatientService
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListScreen(
    patientService: PatientService,
    onPatientClick: (Patient) -> Unit,
    onAddPatientClick: () -> Unit
) {
    var patients by remember { mutableStateOf<List<Patient>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            patients = patientService.getPatients()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Patient List") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddPatientClick) {
                Icon(Icons.Default.Add, contentDescription = "Admit patient")
            }
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(patients) { patient ->
                PatientListItem(patient = patient, onClick = { onPatientClick(patient) })
            }
        }
    }
}