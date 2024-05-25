package com.example.doctsys.ui.screen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.doctsys.ui.screen.LoginDestination
import com.example.doctsys.ui.screen.LoginScreenWithTopBar
import com.example.doctsys.ui.screen.RegistrationDestination
import com.example.doctsys.ui.screen.RegistrationScreenWithTopBar

@Composable
fun DocNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = LoginDestination.route) {
        composable(route = LoginDestination.route) {
            LoginScreenWithTopBar(
                navigateToRegister = { navController.navigate("${RegistrationDestination.route}") }
//                navigateToProfilePage = { navController.navigate("${ProfileDestination.route}/${it}") }
            )
        }
        composable(route = RegistrationDestination.route) {
            RegistrationScreenWithTopBar(
                navigateToLogin = { navController.navigate("${LoginDestination.route}") }
//                navigateToProfilePage = { navController.navigate("${ProfileDestination.route}/${it}") }
            )
        }
    }
}

