package com.example.doctsys.ui.screen

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.doctsys.ui.screen.navigation.DocBottomNavBar
import com.example.doctsys.ui.screen.navigation.NavigationDestination


object ScheduleDestination : NavigationDestination {
    override val route = "schedule"
    override val title = "Schedule"
    const val scheduleIdArg = "scheduleID"
    val routeWithArgs = "$route/{$scheduleIdArg}"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScheduleScreenNavigation(
    navigateToPatients: () -> Unit,
    navigateToSchedules: () -> Unit,
    navigateToProfile: (Int) -> Unit
) {
    Scaffold(
//        topBar = { TopAppBar() },
        content = { ScheduleScreen() },
        bottomBar = {
            DocBottomNavBar(
                navigateToProfile,
                navigateToSchedules,
                navigateToPatients,
                0
            )
        }
    )
}

@Composable
fun ScheduleScreen() {
}