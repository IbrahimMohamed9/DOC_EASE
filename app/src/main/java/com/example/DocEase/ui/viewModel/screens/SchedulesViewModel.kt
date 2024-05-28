package com.example.DocEase.ui.viewModel.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DocEase.model.models.Schedules
import com.example.DocEase.model.repositories.ScheduleRepository
import com.example.DocEase.ui.viewModel.models.SchedulesDetails
import com.example.DocEase.ui.viewModel.models.SchedulesUiState
import com.example.DocEase.ui.viewModel.models.toSchedules
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@RequiresApi(Build.VERSION_CODES.O)
class SchedulesViewModel(private val scheduleRepository: ScheduleRepository) : ViewModel() {
    var schedulesUiState by mutableStateOf(SchedulesUiState())
        private set

    val TodaySchedulesUiStates: StateFlow<SchedulesUiStates> =
        scheduleRepository.getAll()
            .map {
                SchedulesUiStates(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = SchedulesUiStates()
            )

    @Composable
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