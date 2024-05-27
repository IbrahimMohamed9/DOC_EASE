package com.example.DocEase.model.enums

enum class PatientStatus(val dangerLevel: String) {
    STABLE("Low"),
    OBSERVATION("Medium"),
    CRITICAL("High")
}