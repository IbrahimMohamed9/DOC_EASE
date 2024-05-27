package com.example.DocEase.ui.viewModel.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.DocEase.model.enums.PatientStatus
import com.example.DocEase.model.models.Gender
import com.example.DocEase.model.models.Patients
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class PatientsDetails(
    val patientId: Int = 0,
    val name: String = "",
    val surname: String = "",
    val DOJ: String = "${LocalDate.now().dayOfMonth}-${LocalDate.now().monthValue}-${LocalDate.now().year}", //date of join
    val DOB: String = "", //date of birth
    val phoneNumber: String = "",
    val email: String = "",
    val password: String = "",
    val gender: Gender = Gender.FEMALE,
    val description: String = "",
    val status: PatientStatus = PatientStatus.STABLE
)

@RequiresApi(Build.VERSION_CODES.O)
data class PatientsUiState  constructor(
    val patientsDetails: PatientsDetails = PatientsDetails(),
    val isEntryValid: Boolean = false
)

@RequiresApi(Build.VERSION_CODES.O)
fun PatientsDetails.toPatients(): Patients = Patients(
    patientId = patientId,
    name = name,
    surname = surname,
    DOJ = DOJ,
    DOB = DOB,
    phoneNumber = phoneNumber,
    email = email,
    gender = gender,
    description = description,
    status = status,
    password = password
)

@RequiresApi(Build.VERSION_CODES.O)
fun Patients.toPatientsDetails() = PatientsDetails(
    patientId = patientId,
    name = name,
    surname = surname,
    DOJ = DOJ,
    DOB = DOB,
    phoneNumber = phoneNumber,
    gender = gender,
    email = email,
    description = description,
    status = status,
    password = password
)

@RequiresApi(Build.VERSION_CODES.O)
fun Patients.toPatientUiState(isEntryValid: Boolean = false):
        PatientsUiState = PatientsUiState(
    patientsDetails = this.toPatientsDetails(),
    isEntryValid = isEntryValid
)