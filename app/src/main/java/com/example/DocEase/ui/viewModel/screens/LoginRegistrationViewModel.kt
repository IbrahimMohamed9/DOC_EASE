package com.example.DocEase.ui.viewModel.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.DocEase.model.models.Doctors
import com.example.DocEase.model.repositories.DoctorRepository
import com.example.DocEase.ui.viewModel.models.DoctorsDetails
import com.example.DocEase.ui.viewModel.models.DoctorsUiState
import com.example.DocEase.ui.viewModel.models.toDoctorUiState
import com.example.DocEase.ui.viewModel.models.toDoctors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class LoginRegistrationViewModel(
    private val doctorRepository:
    DoctorRepository
) : ViewModel() {
    var doctorsUiState by mutableStateOf(DoctorsUiState())
        private set

    fun updateUiState(doctorsDetails: DoctorsDetails) {
        doctorsUiState = DoctorsUiState(
            doctorsDetails = doctorsDetails, isEntryValid
            = false
        )
    }

    suspend fun register(): Boolean {
        if (validateInput()) {
            doctorRepository.insert(doctorsUiState.doctorsDetails.toDoctors())
            return true
        } else return false
    }

    private suspend fun validateInput(uiState: DoctorsDetails = doctorsUiState.doctorsDetails): Boolean {
        return with(uiState) {
            checkEmail()
        }
    }

    private suspend fun checkEmail(): Boolean {
        return doctorRepository.getEmailDoctor(doctorsUiState.doctorsDetails.email)
            .firstOrNull() == null
    }

    suspend fun getDoctorByEmail(): Flow<Doctors?> {
        return doctorRepository.getEmailDoctor(doctorsUiState.doctorsDetails.email)
    }

    suspend fun login(): Boolean {
        val res = doctorRepository.login(
            doctorsUiState.doctorsDetails.email,
            doctorsUiState.doctorsDetails.password,
        )
            .first()
            ?.toDoctorUiState(true)

        if (res != null) {
            doctorsUiState = res
            return true
        } else {
            return false
        }
    }
}
