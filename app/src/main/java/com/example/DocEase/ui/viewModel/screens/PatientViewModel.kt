package com.example.DocEase.ui.viewModel.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DocEase.model.repositories.PatientRepository
import com.example.DocEase.ui.viewModel.models.PatientsDetails
import com.example.DocEase.ui.viewModel.models.PatientsUiState
import com.example.DocEase.ui.viewModel.models.toPatients
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class PatientViewModel(
    private val patientRepository: PatientRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
//        private val patientId: Int =
//        checkNotNull(savedStateHandle[PatientDestination.patientIdArg])
    var patientsUiState by mutableStateOf(PatientsUiState())
        private set

    init {
        viewModelScope.launch {
//            patientsUiState = patientRepository.getOneStream(patientId)
//                .filterNotNull()
//                .first()
//                .toPatientUiState(true)
        }
    }

    suspend fun updatePatient() {
        patientRepository.update(patientsUiState.patientsDetails.toPatients())
    }

    suspend fun checkPatient(): Boolean {
        return patientRepository.checkPatient(patientsUiState.patientsDetails.toPatients().email)
            .firstOrNull() == null
    }

    suspend fun addPatient() {
        patientRepository.insert(patientsUiState.patientsDetails.toPatients())
    }

    fun updateUiState(patientDetails: PatientsDetails) {
        patientsUiState =
            PatientsUiState(patientsDetails = patientDetails, isEntryValid = false)
    }
}