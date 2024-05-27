package com.example.DocEase.ui.viewModel.models

import com.example.DocEase.model.enums.PatientStatus
import com.example.DocEase.model.models.Patients

data class PatientsDetails(
    val patientId: Int = 0,
    val name: String = "",
    val surname: String = "",
    val DOJ: String = "", //date of join
    val DOB: String = "", //date of birth
    val phoneNumber: String = "",
    val email: String = "",
    val password: String = "",
    val gender: String = "",
    val description: String = "",
    val status: PatientStatus = PatientStatus.STABLE
)


data class PatientsUiState(
    val patientsDetails: PatientsDetails = PatientsDetails(),
    val isEntryValid: Boolean = false
)

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

fun Patients.toPatientUiState(isEntryValid: Boolean = false):
        PatientsUiState = PatientsUiState(
    patientsDetails = this.toPatientsDetails(),
    isEntryValid = isEntryValid
)