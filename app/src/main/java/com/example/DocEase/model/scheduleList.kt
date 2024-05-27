package com.example.DocEase.model

import com.example.DocEase.model.enums.Disease
import com.example.DocEase.model.models.Schedules

object ScheduleList {
    val formattedDates = listOf(
        "2024-08-01",
        "2024-08-02",
        "2024-08-03",
        "2024-08-04",
        "2024-08-05",
        "2024-08-06",
        "2024-08-07"
    )
    val scheduleList = listOf(
        Schedules(0, 1, 123, formattedDates[0], Disease.SORETHROAT, "Regular checkup"),
        Schedules(
            0,
            2, 100,
            formattedDates[1],
            Disease.TEETHPAIN,
            "Prevlksndvksldvsdknvslkvsscription renewal"
        ),
        Schedules(
            0,
            3, 100,
            formattedDates[2],
            Disease.ARMPAIN,
            "Follow-up appointment"
        ),
        Schedules(
            0,
            4, 100,
            formattedDates[3],
            Disease.BACKPAIN,
            "Physical therapy session"
        ),
        Schedules(0, 5, 21, formattedDates[4], Disease.COUGH, "Allergy testing"),
        Schedules(0, 5, 21, formattedDates[4], Disease.COUGH, "Allergy testing"),
        Schedules(0, 5, 21, formattedDates[4], Disease.COUGH, "Allergy testing"),
        Schedules(0, 7, 390, formattedDates[6], Disease.LEGPAIN, "Blood test")
    )
}