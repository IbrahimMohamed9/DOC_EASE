package com.example.DocEase.ui.viewModel.models

import com.example.DocEase.model.enums.Disease
import com.example.DocEase.model.models.Schedules

data class SchedulesDetails(
    val scheduleId: Int = 0,
    val patientId: Int = 0,
    val price: Int = 0,
    val date: String = "",
    val disease: Disease = Disease.COUGH,
    val description: String = ""
)


data class SchedulesUiState(
    val schedulesDetails: SchedulesDetails = SchedulesDetails(),
    val isEntryValid: Boolean = false
)

fun SchedulesDetails.toSchedules(): Schedules = Schedules(
    scheduleId = scheduleId,
    patientId = patientId,
    price = price,
    date = date,
    disease = disease,
    description = description,
)

fun Schedules.toSchedulesDetails() = SchedulesDetails(
    scheduleId = scheduleId,
    patientId = patientId,
    price = price,
    date = date,
    disease = disease,
    description = description,
)

fun Schedules.toScheduleUiState(isEntryValid: Boolean = false):
        SchedulesUiState = SchedulesUiState(
    schedulesDetails = this.toSchedulesDetails(),
    isEntryValid = isEntryValid
)