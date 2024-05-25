package com.example.doctsys.ui.screen

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.doctsys.ui.screen.navigation.NavigationDestination

object ProfileDestination: NavigationDestination {
    override val route = "profile"
    override val title = "Profile"
    const val studentIdArg = "studentID"
    val routeWithArgs = "$route/{$studentIdArg}"
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreenWithTopBar(
    navigateBack: () -> Unit
){
    Scaffold(
    ) {
        ProfileScreen()
    }
}

@Composable
fun ProfileScreen(){}