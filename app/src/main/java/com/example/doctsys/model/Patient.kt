package com.example.doctsys.model

data class Patient(
    val patientId: Int,
    val patientName: String,
    val patientSurName: String,
    val DFJ: String, //date of join
    val DOB: String, //date of birth
    val phoneNumber: Number,
    val email: String,
    val disease: Disease,
    val description: String
)
