package com.example.doctsys.model

enum class PatientStatus(val dangerLevel: String) {
    STABLE("Low"),
    OBSERVATION("Medium"),
    CRITICAL("High")
}