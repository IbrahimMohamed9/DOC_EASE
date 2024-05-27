package com.example.DocEase.ui.screen

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.DocEase.ui.screen.navigation.DocBottomNavBar
import com.example.DocEase.ui.screen.navigation.NavigationDestination
import com.example.DocEase.ui.screen.navigation.TopAppBar


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
    navigateToProfile: (Int) -> Unit,
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = { TopAppBar(PatientDestination.title, navigateBack) },
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

@Preview(showBackground = true)
@Composable
fun PatientScreenPreview() {
    PatientScreen()
}

@Preview(showBackground = true)
@Composable
fun PatientScreenNavigationPreview() {
    PatientScreenNavigation({}, {}, {}, {})
}