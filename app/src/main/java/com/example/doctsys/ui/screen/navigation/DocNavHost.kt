package com.example.doctsys.ui.screen.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.doctsys.ui.screen.PatientDestination
import com.example.doctsys.ui.screen.PatientScreenNavigation
import com.example.doctsys.ui.screen.PatientsDestination
import com.example.doctsys.ui.screen.PatientsScreenNavigation
import com.example.doctsys.ui.screen.ProfileDestination
import com.example.doctsys.ui.screen.ProfileScreenNavigation
import com.example.doctsys.ui.screen.ScheduleDestination
import com.example.doctsys.ui.screen.ScheduleScreenNavigation
import com.example.doctsys.ui.screen.SchedulesDestination
import com.example.doctsys.ui.screen.SchedulesScreenNavigation
import com.example.doctsys.ui.screen.enterToApp.LoginDestination
import com.example.doctsys.ui.screen.enterToApp.LoginScreenNavigation
import com.example.doctsys.ui.screen.enterToApp.RegistrationDestination
import com.example.doctsys.ui.screen.enterToApp.RegistrationScreenNavigation

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DocNavHost(
    navController: NavHostController
) {
    // TODO ensure this is login
    NavHost(navController = navController, startDestination = LoginDestination.route) {
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

        composable(
            route = PatientDestination.routeWithArgs,
            arguments = listOf(navArgument(PatientDestination.patientIdArg) {
                type = NavType.IntType
            })
        ) {
            PatientScreenNavigation(
                navigateToSchedules = { navController.navigate(SchedulesDestination.route) },
                navigateToPatients = { navController.navigate(PatientsDestination.route) },
                navigateToProfile = { navController.navigate("${ProfileDestination.route}/${it}") },
                navigateBack = { navController.navigateUp() }
            )
        }

        composable(
            route = ScheduleDestination.routeWithArgs,
            arguments = listOf(navArgument(ScheduleDestination.scheduleIdArg) {
                type = NavType.IntType
            })
        ) {
            ScheduleScreenNavigation(
                navigateToSchedules = { navController.navigate(SchedulesDestination.route) },
                navigateToPatients = { navController.navigate(PatientsDestination.route) },
                navigateToProfile = { navController.navigate("${ProfileDestination.route}/${it}") },
                navigateBack = { navController.navigateUp() }
            )
        }

        composable(route = PatientsDestination.route) {
            PatientsScreenNavigation(
                navigateToProfile = { navController.navigate("${ProfileDestination.route}/${it}") },
                navigateToSchedules = { navController.navigate(SchedulesDestination.route) },
                3,
                navigateToPatient = { navController.navigate("${PatientDestination.route}/${it}") },
            )
        }

        composable(route = SchedulesDestination.route) {
            SchedulesScreenNavigation(
                navigateToProfile = { navController.navigate("${ProfileDestination.route}/${it}") },
                navigateToPatients = {navController.navigate(PatientsDestination.route)},
                0,
                navigateToSchedule = { navController.navigate("${ScheduleDestination.route}/${it}") },

            )
        }
    }
}

