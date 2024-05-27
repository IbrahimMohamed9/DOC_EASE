package com.example.doctsys.ui.screen

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.doctsys.ui.screen.navigation.DocBottomNavBar
import com.example.doctsys.ui.screen.navigation.NavigationDestination
import com.example.doctsys.ui.screen.navigation.TopAppBar


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
    navigateToSchedules: () -> Unit, navigateToProfile: (Int) -> Unit,
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(ScheduleDestination.title, navigateBack) },
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

@Preview(showBackground = true)
@Composable
fun ScheduleScreenPreview() {
    ScheduleScreen()
}

@Preview(showBackground = true)
@Composable
fun ScheduleScreenNavigationPreview() {
    ScheduleScreenNavigation({}, {}, {}, {})
}