package com.example.DocEase.ui.viewModel.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DocEase.model.repositories.DoctorRepository
import com.example.DocEase.ui.screen.ProfileDestination
import com.example.DocEase.ui.viewModel.models.DoctorsDetails
import com.example.DocEase.ui.viewModel.models.DoctorsUiState
import com.example.DocEase.ui.viewModel.models.toDoctorUiState
import com.example.DocEase.ui.viewModel.models.toDoctors
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class DoctorViewModal(
    private val doctorRepository: DoctorRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val doctorId: Int =
        checkNotNull(savedStateHandle[ProfileDestination.doctorIdArg])
    var doctorsUiState by mutableStateOf(DoctorsUiState())
        private set

    init {
        viewModelScope.launch {
            doctorsUiState = doctorRepository.getOneStream(doctorId)
                .filterNotNull()
                .first()
                .toDoctorUiState(true)
        }
    }

    suspend fun updateDoctor() {
        doctorRepository.update(doctorsUiState.doctorsDetails.toDoctors())
    }

    //FOR LOGOUT
    fun clearUi() {
        doctorsUiState = DoctorsUiState()
    }

    fun updateUiState(doctorDetails: DoctorsDetails) {
        doctorsUiState =
            DoctorsUiState(doctorsDetails = doctorDetails, isEntryValid = false)
    }
}