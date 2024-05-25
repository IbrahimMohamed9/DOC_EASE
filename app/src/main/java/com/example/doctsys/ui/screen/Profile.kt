package com.example.doctsys.ui.screen

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.doctsys.ui.screen.navigation.DocBottomNavBar
import com.example.doctsys.ui.screen.navigation.NavigationDestination

object ProfileDestination: NavigationDestination {
    override val route = "profile"
    override val title = "Profile"
    const val doctorIdArg = "doctorID"
    val routeWithArgs = "$route/{$doctorIdArg}"
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreenNavigation(
    navigateToPatients: ()->Unit,
    navigateToSchedules: ()->Unit) {
    Scaffold(
        bottomBar = { DocBottomNavBar({},navigateToSchedules, navigateToPatients,0) }
    ) {
        ProfileScreen()
    }
}

@Composable
fun ProfileScreen() {
    Text(text = "hello from profile")
}