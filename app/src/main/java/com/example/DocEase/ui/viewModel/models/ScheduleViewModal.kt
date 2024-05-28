package com.example.DocEase.ui.viewModel.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.DocEase.model.enums.Disease
import com.example.DocEase.model.models.Schedules
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class SchedulesDetails constructor(
    val scheduleId: Int = 0,
    val patientId: Int = 0,
    val price: Int = 100,
    val date: String = "${LocalDate.now().plusDays(1).dayOfMonth}-${LocalDate.now().plusDays(1).monthValue}-${LocalDate.now().plusDays(1).year}",
    val disease: Disease = Disease.COUGH,
    val description: String = ""
)

@RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
fun Schedules.toSchedulesDetails() = SchedulesDetails(
    scheduleId = scheduleId,
    patientId = patientId,
    price = price,
    date = date,
    disease = disease,
    description = description,
)

@RequiresApi(Build.VERSION_CODES.O)
fun Schedules.toScheduleUiState(isEntryValid: Boolean = false):
        SchedulesUiState = SchedulesUiState(
    schedulesDetails = this.toSchedulesDetails(),
    isEntryValid = isEntryValid
)