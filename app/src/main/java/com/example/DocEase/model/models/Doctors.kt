package com.example.DocEase.model.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doctors")
data class Doctors(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "doctorId")
    val doctorId: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "surname") val surname: String,
    @ColumnInfo(name = "DOJ") val DOJ: String, //date of join
    @ColumnInfo(name = "DOB") val DOB: String, //date of birth
    @ColumnInfo(name = "phoneNumber") val phoneNumber: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "gender") val gender: String,
)
