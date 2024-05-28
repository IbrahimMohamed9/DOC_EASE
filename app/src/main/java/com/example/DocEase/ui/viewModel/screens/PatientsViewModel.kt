package com.example.DocEase.ui.viewModel.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DocEase.model.models.Patients
import com.example.DocEase.model.repositories.PatientRepository
import com.example.DocEase.ui.viewModel.models.PatientsDetails
import com.example.DocEase.ui.viewModel.models.PatientsUiState
import com.example.DocEase.ui.viewModel.models.toPatients
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@RequiresApi(Build.VERSION_CODES.O)
class PatientsViewModel(private val patientRepository: PatientRepository) : ViewModel() {
    var patientsUiState by mutableStateOf(PatientsUiState())
        private set

    val PatientsUiStates: StateFlow<PatientsUiStates> =
        patientRepository.getAll()
            .map {
                PatientsUiStates(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = PatientsUiStates()
            )

    suspend fun checkPatient(): Boolean {
        return patientRepository.checkPatient(patientsUiState.patientsDetails.toPatients().email)
            .firstOrNull() == null
    }

    suspend fun addPatient() {
        patientRepository.insert(patientsUiState.patientsDetails.toPatients())
    }

    fun updateUiState(patientDetails: PatientsDetails) {
        patientsUiState =
            PatientsUiState(
                patientsDetails = patientDetails,
                isEntryValid = false
            )
    }
}

data class PatientsUiStates(val patientList: List<Patients> = listOf())