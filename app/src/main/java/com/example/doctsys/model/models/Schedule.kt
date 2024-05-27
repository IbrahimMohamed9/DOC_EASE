package com.example.doctsys.model.models

import com.example.doctsys.model.Disease

data class Schedule(
    val scheduleId: Int,
    val patientId: Int,
    val price: Int,
    val patientName: String,
    val date: String,
    val disease: Disease,
    val description: String
)
