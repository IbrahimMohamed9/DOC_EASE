package com.example.doctsys.ui.screen

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.doctsys.ui.screen.navigation.DocBottomNavBar
import com.example.doctsys.ui.screen.navigation.NavigationDestination
import com.example.doctsys.ui.screen.navigation.TopAppBar


object PatientDestination : NavigationDestination {
    override val route = "patient"
    override val title = "Patient"
    const val patientIdArg = "patientID"
    val routeWithArgs = "$route/{$patientIdArg}"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PatientScreenNavigation(
    navigateToPatients: () -> Unit,
    navigateToSchedules: () -> Unit,
    navigateToProfile: (Int) -> Unit
) {
    Scaffold(
//        topBar = { TopAppBar() },
        content = { PatientScreen() },
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
fun PatientScreen() {
}