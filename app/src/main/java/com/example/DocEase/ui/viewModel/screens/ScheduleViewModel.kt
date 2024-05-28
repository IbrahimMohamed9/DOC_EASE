package com.example.DocEase.ui.viewModel.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DocEase.model.models.Schedules
import com.example.DocEase.model.repositories.PatientRepository
import com.example.DocEase.model.repositories.ScheduleRepository
import com.example.DocEase.ui.screen.ScheduleDestination
import com.example.DocEase.ui.viewModel.models.PatientsDetails
import com.example.DocEase.ui.viewModel.models.PatientsUiState
import com.example.DocEase.ui.viewModel.models.SchedulesDetails
import com.example.DocEase.ui.viewModel.models.SchedulesUiState
import com.example.DocEase.ui.viewModel.models.toPatientUiState
import com.example.DocEase.ui.viewModel.models.toPatients
import com.example.DocEase.ui.viewModel.models.toScheduleUiState
import com.example.DocEase.ui.viewModel.models.toSchedules
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class ScheduleViewModel(
    private val scheduleRepository: ScheduleRepository,
    private val patientRepository: PatientRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val scheduleId: Int =
        checkNotNull(savedStateHandle[ScheduleDestination.scheduleIdArg])

    var schedulesUiState by mutableStateOf(SchedulesUiState())
        private set

    var patientsUiState by mutableStateOf(PatientsUiState())
        private set

    fun getPatient(patientId: Int) {
        viewModelScope.launch {
            patientRepository.getOneStream(patientId)
                .filterNotNull()
                .first()
                .let { patient ->
                    patientsUiState = patient.toPatientUiState(true)
                }
        }
    }

    init {
        viewModelScope.launch {
            schedulesUiState = scheduleRepository.getOneStream(scheduleId)
                .filterNotNull()
                .first()
                .toScheduleUiState(true)
        }
    }

    suspend fun updateSchedule() {
        scheduleRepository.update(schedulesUiState.schedulesDetails.toSchedules())
    }

    fun updateUiState(scheduleDetails: SchedulesDetails) {
        schedulesUiState =
            SchedulesUiState(schedulesDetails = scheduleDetails, isEntryValid = false)
    }

    fun updateUiState(patientsDetails: PatientsDetails) {
        patientsUiState =
            PatientsUiState(patientsDetails = patientsDetails, isEntryValid = false)
    }

    suspend fun updatePatient() {
        patientRepository.update(patientsUiState.patientsDetails.toPatients())
    }
}