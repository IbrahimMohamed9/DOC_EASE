package com.example.DocEase.model.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.DocEase.model.enums.PatientStatus
import org.jetbrains.annotations.NotNull

@Entity(tableName = "patients")
data class Patients(
    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "patientId")
    val patientId: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "surname")
    val surname: String,

    @ColumnInfo(name = "DOJ")
    val DOJ: String, //date of join

    @ColumnInfo(name = "DOB")
    val DOB: String, //date of birth

    @ColumnInfo(name = "phoneNumber")
    val phoneNumber: Number,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "gender")
    val gender: String,

    @ColumnInfo(name = "status")
    val status: PatientStatus
)
