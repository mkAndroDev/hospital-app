package com.erservice.app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.erservice.app.api.Patient
import com.erservice.app.api.PatientService
import com.erservice.app.api.dto.UpdatePatientStatusRequest
import kotlinx.coroutines.launch
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientDetailsScreen(patient: Patient, patientService: PatientService, onBack: () -> Unit) {
    var status by remember { mutableStateOf(patient.status) }
    val statuses = listOf("NEW", "IN_PROGRESS", "TREATED")
    var expanded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Patient Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DetailCard(patient)

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = status,
                    onValueChange = {},
                    label = { Text("Status") },
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    statuses.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                status = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    coroutineScope.launch {
                        patientService.updatePatientStatus(patient.id, UpdatePatientStatusRequest(status))
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}

@Composable
private fun DetailCard(patient: Patient) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            InfoRow("Name:", "${patient.firstName} ${patient.lastName}")
            InfoRow("PESEL:", patient.pesel)
            InfoRow("Admission date:", formatAdmittedAt(patient.createdAt))
            InfoRow("Condition:", patient.condition, contentColor = stanToColor(patient.condition))
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String, contentColor: Color = Color.Unspecified) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$label ",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = contentColor
        )
    }
}

private fun formatAdmittedAt(admittedAt: String): String {
    return try {
        val dateTime = admittedAt.toLocalDateTime()
        val date = "${dateTime.dayOfMonth.toString().padStart(2, '0')}/${dateTime.monthNumber.toString().padStart(2, '0')}/${dateTime.year}"
        val time = "${dateTime.hour.toString().padStart(2, '0')}:${dateTime.minute.toString().padStart(2, '0')}"
        "$date $time"
    } catch (e: Exception) {
        admittedAt // Fallback to original string if parsing fails
    }
}

@Composable
private fun stanToColor(condition: String): Color {
    return when (condition.uppercase()) {
        "RED" -> MaterialTheme.colorScheme.error
        "ORANGE" -> Color(0xFFFFA500)
        "YELLOW" -> Color(0xFFFBC02D)
        "GREEN" -> Color(0xFF388E3C)
        "BROWN" -> Color(0xFFA1887F)
        "BLACK" -> Color.Black
        else -> Color.Unspecified
    }
}
