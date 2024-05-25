package com.example.doctsys.model

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
        Schedule(0, 1, "John Doe", formattedDates[0], Disease.ARMPAIN, "Regular checkup"),
        Schedule(
            0,
            2,
            "Jane Smith",
            formattedDates[1],
            Disease.TEETHPAIN,
            "Prevlksndvksldvsdknvslkvsscription renewal"
        ),
        Schedule(
            0,
            3,
            "Alice Johnson",
            formattedDates[2],
            Disease.ARMPAIN,
            "Follow-up appointment"
        ),
        Schedule(
            0,
            4,
            "Bob Brown",
            formattedDates[3],
            Disease.BACKPAIN,
            "Physical therapy session"
        ),
        Schedule(0, 5, "Emily Davis", formattedDates[4], Disease.TEETHPAIN, "Allergy testing"),
        Schedule(
            0,
            6,
            "Michael Wilson",
            formattedDates[5],
            Disease.LEGPAIN,
            "Gastroenterology consultation"
        ),
        Schedule(0, 7, "Emma Martinez", formattedDates[6], Disease.LEGPAIN, "Blood test")
    )
}