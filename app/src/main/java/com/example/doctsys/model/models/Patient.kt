package com.example.doctsys.model.models

import com.example.doctsys.model.PatientStatus

data class Patient(
    val patientId: Int,
    val patientName: String,
    val patientSurName: String,
    val DOJ: String, //date of join
    val DOB: String, //date of birth
    val phoneNumber: Number,
    val email: String,
    val description: String,
    val gender: String,
    val status: PatientStatus
)
