package com.example.DocEase.ui.screen.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.DocEase.ui.screen.LoginDestination
import com.example.DocEase.ui.screen.LoginScreenNavigation
import com.example.DocEase.ui.screen.PatientDestination
import com.example.DocEase.ui.screen.PatientScreenNavigation
import com.example.DocEase.ui.screen.PatientsDestination
import com.example.DocEase.ui.screen.PatientsScreenNavigation
import com.example.DocEase.ui.screen.ProfileDestination
import com.example.DocEase.ui.screen.ProfileScreenNavigation
import com.example.DocEase.ui.screen.RegistrationDestination
import com.example.DocEase.ui.screen.RegistrationScreenNavigation
import com.example.DocEase.ui.screen.ScheduleDestination
import com.example.DocEase.ui.screen.ScheduleScreenNavigation
import com.example.DocEase.ui.screen.SchedulesDestination
import com.example.DocEase.ui.screen.SchedulesScreenNavigation

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DocNavHost(
    navController: NavHostController
) {
    var doctorId = 0
    NavHost(navController = navController, startDestination = LoginDestination.route) {
        composable(route = LoginDestination.route) {
            LoginScreenNavigation(
                navigateToRegister = { navController.navigate(RegistrationDestination.route) },
                navigateToProfilePage = { navController.navigate("${ProfileDestination.route}/${it}"); doctorId = it }
            )
        }

        composable(route = RegistrationDestination.route) {
            RegistrationScreenNavigation(
                navigateToLogin = { navController.navigate(LoginDestination.route) },
                navigateToProfilePage = { navController.navigate("${ProfileDestination.route}/${it}"); doctorId = it }
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
                navigateToPatients = { navController.navigate(PatientsDestination.route) }
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
                navigateToProfile = { navController.navigate("${ProfileDestination.route}/${doctorId}") },
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
                navigateToProfile = { navController.navigate("${ProfileDestination.route}/${doctorId}") },
                navigateBack = { navController.navigateUp() }
            )
        }

        composable(route = PatientsDestination.route) {
            PatientsScreenNavigation(
                navigateToProfile = { navController.navigate("${ProfileDestination.route}/${doctorId}") },
                navigateToSchedules = { navController.navigate(SchedulesDestination.route) },
                navigateToPatient = { navController.navigate("${PatientDestination.route}/${it}") },
            )
        }

        composable(route = SchedulesDestination.route) {
            SchedulesScreenNavigation(
                navigateToProfile = { navController.navigate("${ProfileDestination.route}/${doctorId}") },
                navigateToPatients = {navController.navigate(PatientsDestination.route)},
                navigateToSchedule = { navController.navigate("${ScheduleDestination.route}/${it}") },
            )
        }
    }
}

