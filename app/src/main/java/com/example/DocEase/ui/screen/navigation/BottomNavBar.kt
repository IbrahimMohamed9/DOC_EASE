package com.example.DocEase.ui.screen.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.DocEase.R

@Composable
fun DocBottomNavBar(
    navigateToProfile: () -> Unit,
    navigateToSchedules: () -> Unit,
    navigateToPatients: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 50.dp)
        ) {
            IconButton(onClick = { navigateToProfile() }) {
                Icon(
                    painter = painterResource(id = R.drawable.toppng_com_file_svg_profile_icon_vector_980x980),
                    contentDescription = "profile Icon",
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { navigateToSchedules() }) {
                Icon(
                    painter = painterResource(id = R.drawable.daily_schedule_icon),
                    contentDescription = "schedule Icon",
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { navigateToPatients() }) {
                Icon(
                    painter = painterResource(id = R.drawable.patient_hospital_stretcher_icon),
                    contentDescription = "patients Icon",
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DocBottomNavBarPreview() {
    DocBottomNavBar({}, {}, {})
}
