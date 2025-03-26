package com.cr.studentregistry.presentation.features.studentListScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.cr.studentregistry.domain.models.Student

@Composable
fun StudentGridItem(
    student: Student,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Profile Image Placeholder
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    )
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Student Details
            Text(
                text = "Id: ${student.id}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Name: ${student.name}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Contact: ${student.phoneNumber}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Gender: ${student.gender}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Email: ${student.email}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Address: ${student.address}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "DOB: ${student.dateOfBirth}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Registration Fees: ${student.registrationFees}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Update Button
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update")
            }
        }
    }
}