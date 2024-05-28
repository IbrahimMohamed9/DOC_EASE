package com.example.DocEase.ui.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.DocEase.DocEaseApplication
import com.example.DocEase.ui.viewModel.screens.DoctorViewModal
import com.example.DocEase.ui.viewModel.screens.LoginRegistrationViewModel
import com.example.DocEase.ui.viewModel.screens.PatientViewModel
import com.example.DocEase.ui.viewModel.screens.PatientsViewModel
import com.example.DocEase.ui.viewModel.screens.ScheduleViewModel
import com.example.DocEase.ui.viewModel.screens.SchedulesViewModel

object AppViewModelProvider {
    @RequiresApi(Build.VERSION_CODES.O)
    val Factory = viewModelFactory {
        initializer {
            LoginRegistrationViewModel(
                DocEaseApplication().container.doctorRepository
            )
        }
        initializer {
            DoctorViewModal(
                DocEaseApplication().container.doctorRepository,
                this.createSavedStateHandle()
            )
        }
        initializer {
            PatientViewModel(
                DocEaseApplication().container.patientRepository,
                this.createSavedStateHandle()
            )
        }
        initializer {
            PatientsViewModel(
                DocEaseApplication().container.patientRepository
            )
        }
        initializer {
            SchedulesViewModel(
                DocEaseApplication().container.scheduleRepository,
                DocEaseApplication().container.patientRepository
            )
        }
        initializer {
            ScheduleViewModel(
                DocEaseApplication().container.scheduleRepository,
                DocEaseApplication().container.patientRepository,
                this.createSavedStateHandle()
            )
        }
    }
}

fun CreationExtras.DocEaseApplication(): DocEaseApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as
            DocEaseApplication)
