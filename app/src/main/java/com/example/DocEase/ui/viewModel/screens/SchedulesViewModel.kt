package com.example.DocEase.ui.viewModel.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DocEase.model.models.Schedules
import com.example.DocEase.model.repositories.PatientRepository
import com.example.DocEase.model.repositories.ScheduleRepository
import com.example.DocEase.ui.viewModel.models.PatientsUiState
import com.example.DocEase.ui.viewModel.models.SchedulesDetails
import com.example.DocEase.ui.viewModel.models.SchedulesUiState
import com.example.DocEase.ui.viewModel.models.toPatientUiState
import com.example.DocEase.ui.viewModel.models.toSchedules
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class SchedulesViewModel(
    private val scheduleRepository: ScheduleRepository,
    private val patientRepository: PatientRepository,
) : ViewModel() {
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

    suspend fun checkPatientID(patientId: Int): Boolean =
        patientRepository.checkPatientID(patientId).firstOrNull() != null


    val TodaySchedulesUiStates: StateFlow<SchedulesUiStates> =
        scheduleRepository.getSchedulesByDate("${LocalDate.now().dayOfMonth}-${LocalDate.now().monthValue}-${LocalDate.now().year}")
            .map {
                SchedulesUiStates(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = SchedulesUiStates()
            )

    fun getSchedulesByDate(date: String): StateFlow<SchedulesUiStates> {
        return scheduleRepository.getSchedulesByDate(date)
            .map {
                SchedulesUiStates(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = SchedulesUiStates()
            )
    }


    suspend fun addSchedule() {
        scheduleRepository.insert(schedulesUiState.schedulesDetails.toSchedules())
    }

    fun updateUiState(scheduleDetails: SchedulesDetails) {
        schedulesUiState =
            SchedulesUiState(
                schedulesDetails = scheduleDetails,
                isEntryValid = false
            )
    }
}

data class SchedulesUiStates(val scheduleList: List<Schedules> = listOf())