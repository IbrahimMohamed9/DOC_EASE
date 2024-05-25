package com.example.doctsys.ui.screen

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.doctsys.ui.screen.navigation.DocBottomNavBar
import com.example.doctsys.ui.screen.navigation.NavigationDestination

object SchedulesDestination : NavigationDestination {
    override val route = "schedule"
    override val title = "schedules"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScheduleScreenNavigation(
    navigateToProfile: (Int) -> Unit,
    navigateToPatients: () -> Unit,
    profileId: Int
) {
    Scaffold(
        bottomBar = {
            DocBottomNavBar(
                navigateToProfile,
                { },
                navigateToPatients,
                profileId
            )
        }
    ) {
        SchedulesScreen()
    }
}

@Composable
fun SchedulesScreen() {
    Text(text = "hellow form schedule")
}