package com.example.DocEase.model.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "doctors")
data class Doctors(
    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "doctorId")
    val doctorId: Int = 0,

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

    @ColumnInfo(name = "gender")
    val gender: String,
)
