package com.example.DocEase.model.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.DocEase.model.enums.Disease
import org.jetbrains.annotations.NotNull

@Entity(tableName = "schedules")
data class Schedules(
    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "scheduleId")
    val scheduleId: Int,

    @ColumnInfo(name = "patientId")
    val patientId: Int,

    @ColumnInfo(name = "price")
    val price: Int,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "disease")
    val disease: Disease,

    @ColumnInfo(name = "description")
    val description: String
)
