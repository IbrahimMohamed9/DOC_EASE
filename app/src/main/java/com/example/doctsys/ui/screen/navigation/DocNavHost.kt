package com.example.doctsys.ui.screen.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.doctsys.ui.screen.LoginDestination
import com.example.doctsys.ui.screen.LoginScreenNavigation
import com.example.doctsys.ui.screen.PatientScreenNavigation
import com.example.doctsys.ui.screen.PatientsDestination
import com.example.doctsys.ui.screen.ProfileDestination
import com.example.doctsys.ui.screen.ProfileScreenNavigation
import com.example.doctsys.ui.screen.RegistrationDestination
import com.example.doctsys.ui.screen.RegistrationScreenNavigation
import com.example.doctsys.ui.screen.ScheduleScreenNavigation
import com.example.doctsys.ui.screen.SchedulesDestination

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DocNavHost(
    navController: NavHostController
) {
    // TODO ensure this is login
    NavHost(navController = navController, startDestination = SchedulesDestination.route) {
        composable(route = LoginDestination.route) {
            LoginScreenNavigation(
                navigateToRegister = { navController.navigate(RegistrationDestination.route) },
                navigateToProfilePage = { navController.navigate("${ProfileDestination.route}/${it}") }
            )
        }

        composable(route = RegistrationDestination.route) {
            RegistrationScreenNavigation(
                navigateToLogin = { navController.navigate(LoginDestination.route) },
                navigateToProfilePage = { navController.navigate("${ProfileDestination.route}/${it}") }
            )
        }

        composable(
            route = ProfileDestination.routeWithArgs,
            arguments = listOf(navArgument(ProfileDestination.doctorIdArg) {
                type = NavType.IntType
            })
        ) {
            ProfileScreenNavigation(
                navigateToSchedules = { navController.navigate(SchedulesDestination.route) },
                navigateToPatients = {navController.navigate(PatientsDestination.route)}
            )
        }

        composable(route = PatientsDestination.route) {
            PatientScreenNavigation(
                navigateToProfile = { navController.navigate("${ProfileDestination.route}/${it}") },
                navigateToSchedules = { navController.navigate(SchedulesDestination.route) },
                3
            )
        }

        composable(route = SchedulesDestination.route) {
            ScheduleScreenNavigation(
                navigateToProfile = { navController.navigate("${ProfileDestination.route}/${it}") },
                navigateToPatients = {navController.navigate(PatientsDestination.route)},
                0
            )
        }
    }
}

