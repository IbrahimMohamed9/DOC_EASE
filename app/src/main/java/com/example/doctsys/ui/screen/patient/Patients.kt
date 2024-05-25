package com.example.doctsys.ui.screen.patient

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.doctsys.ui.screen.navigation.DocBottomNavBar
import com.example.doctsys.ui.screen.navigation.NavigationDestination

object PatientsDestination : NavigationDestination {
    override val route = "patients"
    override val title = "Patients"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PatientScreenNavigation(
    navigateToProfile: (Int)->Unit,
    navigateToSchedules: ()->Unit,
    profileId: Int
) {
    Scaffold(
        bottomBar = { DocBottomNavBar(navigateToProfile, navigateToSchedules, {},profileId) }
    ) {
        PatientsScreen()
    }
}

@Composable
fun PatientsScreen() {
    Text(text = "Hello from Patients")
}