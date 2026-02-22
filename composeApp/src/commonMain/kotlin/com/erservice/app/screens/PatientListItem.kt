package com.erservice.app.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.erservice.app.api.Patient
import kotlinx.datetime.toLocalDateTime

@Composable
fun PatientListItem(patient: Patient, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${patient.firstName} ${patient.lastName}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow("Przyjęcie:", formatAdmittedAt(patient.createdAt))
            InfoRow("Status:", patient.status)
            InfoRow("Stan:", patient.condition, contentColor = stanToColor(patient.condition))
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String, contentColor: Color = Color.Unspecified) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$label ",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
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
        "GREEN" -> Color(0xFF388E3C) // A standard green color
        "YELLOW" -> Color(0xFFFBC02D) // A standard yellow color
        else -> Color.Unspecified
    }
}