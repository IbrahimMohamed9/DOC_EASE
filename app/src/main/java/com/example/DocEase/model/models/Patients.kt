package com.example.DocEase.model.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.DocEase.model.enums.Gender
import com.example.DocEase.model.enums.PatientStatus

@Entity(tableName = "patients")
data class Patients(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "patientId")
    val patientId: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "surname") val surname: String,
    @ColumnInfo(name = "DOJ") val DOJ: String, //date of join
    @ColumnInfo(name = "DOB") val DOB: String, //date of birth
    @ColumnInfo(name = "phoneNumber") val phoneNumber: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "gender") val gender: Gender,
    @ColumnInfo(name = "status") val status: PatientStatus
)
