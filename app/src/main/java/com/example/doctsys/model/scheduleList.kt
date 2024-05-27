package com.example.doctsys.model

import com.example.doctsys.model.models.Schedule

val formattedDates = listOf(
    "2024-08-01",
    "2024-08-02",
    "2024-08-03",
    "2024-08-04",
    "2024-08-05",
    "2024-08-06",
    "2024-08-07"
)
object ScheduleList {
    val scheduleList = listOf(
        Schedule(0, 1, 123, "John Doe", formattedDates[0], Disease.SORETHROAT, "Regular checkup"),
        Schedule(
            0,
            2, 100,
            "Jane Smith",
            formattedDates[1],
            Disease.TEETHPAIN,
            "Prevlksndvksldvsdknvslkvsscription renewal"
        ),
        Schedule(
            0,
            3, 100,
            "Alice Johnson",
            formattedDates[2],
            Disease.ARMPAIN,
            "Follow-up appointment"
        ),
        Schedule(
            0,
            4, 100,
            "Bob Brown",
            formattedDates[3],
            Disease.BACKPAIN,
            "Physical therapy session"
        ),
        Schedule(0, 5, 21, "Emily Davis", formattedDates[4], Disease.COUGH, "Allergy testing"),
        Schedule(0, 5, 21, "Emily Davis", formattedDates[4], Disease.COUGH, "Allergy testing"),
        Schedule(0, 5, 21, "Emily Davis", formattedDates[4], Disease.COUGH, "Allergy testing"),
        Schedule(0, 7, 390, "Emma Martinez", formattedDates[6], Disease.LEGPAIN, "Blood test")
    )
}