package com.example.DocEase.ui.viewModel.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DocEase.model.models.Patients
import com.example.DocEase.model.repositories.PatientRepository
import com.example.DocEase.ui.screen.PatientDestination
import com.example.DocEase.ui.viewModel.models.PatientsDetails
import com.example.DocEase.ui.viewModel.models.PatientsUiState
import com.example.DocEase.ui.viewModel.models.toPatientUiState
import com.example.DocEase.ui.viewModel.models.toPatients
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class PatientViewModel(
    private val patientRepository: PatientRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
        private val patientId: Int =
        checkNotNull(savedStateHandle[PatientDestination.patientIdArg])
    var patientsUiState by mutableStateOf(PatientsUiState())
        private set

    init {
        viewModelScope.launch {
            patientsUiState = patientRepository.getOneStream(patientId)
                .filterNotNull()
                .first()
                .toPatientUiState(true)
        }
    }

    suspend fun updatePatient() {
        patientRepository.update(patientsUiState.patientsDetails.toPatients())
    }

    fun getPatient(): Flow<Patients?> {
        return patientRepository.getOneStream(patientsUiState.patientsDetails.toPatients().patientId)
    }

    fun updateUiState(patientDetails: PatientsDetails) {
        patientsUiState =
            PatientsUiState(patientsDetails = patientDetails, isEntryValid = false)
    }
}