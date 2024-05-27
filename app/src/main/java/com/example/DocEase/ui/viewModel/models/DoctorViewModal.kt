package com.example.DocEase.ui.viewModel.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.DocEase.model.enums.MedicalSpecialization
import com.example.DocEase.model.models.Doctors
import com.example.DocEase.model.models.Gender
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
val currentTime = LocalDate.now()
data class DoctorsDetails @RequiresApi(Build.VERSION_CODES.O) constructor(
    val doctorId: Int = 0,
    val name: String = "",
    val surname: String = "",
    val DOJ: String = "${currentTime.dayOfMonth}-${currentTime.monthValue}-${currentTime.year}", //date of join
    val DOB: String = "", //date of birth
    val phoneNumber: String = "",
    val email: String = "",
    val gender: Gender = Gender.FEMALE,
    val medicalSpecialization: MedicalSpecialization = MedicalSpecialization.CARDIOLOGY,
    val password: String = "",
)

@RequiresApi(Build.VERSION_CODES.O)
data class DoctorsUiState(
    val doctorsDetails: DoctorsDetails = DoctorsDetails(),
    val isEntryValid: Boolean = false
)

fun DoctorsDetails.toDoctors(): Doctors = Doctors(
    doctorId = doctorId,
    name = name,
    surname = surname,
    DOJ = DOJ,
    DOB = DOB,
    phoneNumber = phoneNumber,
    email = email,
    gender = gender,
    password = password,
    medicalSpecialization = medicalSpecialization
    )

@RequiresApi(Build.VERSION_CODES.O)
fun Doctors.toDoctorsDetails() = DoctorsDetails(
    doctorId = doctorId,
    name = name,
    surname = surname,
    DOJ = DOJ,
    DOB = DOB,
    phoneNumber = phoneNumber,
    email = email,
    gender = gender,
    password = password,
    medicalSpecialization = medicalSpecialization
)

@RequiresApi(Build.VERSION_CODES.O)
fun Doctors.toDoctorUiState(isEntryValid: Boolean = false):
        DoctorsUiState = DoctorsUiState(
    doctorsDetails = this.toDoctorsDetails(),
    isEntryValid = isEntryValid
)