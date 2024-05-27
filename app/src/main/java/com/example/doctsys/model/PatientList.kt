package com.example.doctsys.model

import com.example.doctsys.model.models.Patient

object PatientList {
    val patientList = listOf(
        Patient(
            1,
            "John",
            "Doe",
            "2021-01-01",
            "1980-05-15",
            1234567890,
            "john.doe@example.com",
            "Patient with mild symptoms.",
            "Male",
            PatientStatus.STABLE
        ),
        Patient(
            2,
            "Jane",
            "Smith",
            "2021-02-14",
            "1975-03-20",
            9876543210,
            "jane.smith@example.com",
            "Patient recovering from surgery.",
            "Female",
            PatientStatus.OBSERVATION
        ),
        Patient(
            3,
            "Robert",
            "Brown",
            "2021-03-10",
            "1990-07-11",
            4561237890,
            "robert.brown@example.com",
            "Patient with chronic illness.",
            "Male",
            PatientStatus.CRITICAL
        ),
        Patient(
            4,
            "Emily",
            "Jones",
            "2021-04-25",
            "1985-12-01",
            7890123456,
            "emily.jones@example.com",
            "Patient with high blood pressure.",
            "Female",
            PatientStatus.OBSERVATION
        ),
        Patient(
            5,
            "Michael",
            "Davis",
            "2021-05-30",
            "1982-08-19",
            3216549870,
            "michael.davis@example.com",
            "Patient with diabetes.",
            "Male",
            PatientStatus.STABLE
        ),
        Patient(
            6,
            "Sarah",
            "Wilson",
            "2021-06-17",
            "1995-09-23",
            6549873210,
            "sarah.wilson@example.com",
            "Patient with allergy symptoms.",
            "Female",
            PatientStatus.STABLE
        ),
        Patient(
            7,
            "David",
            "Martinez",
            "2021-07-08",
            "1992-11-05",
            7418529630,
            "david.martinez@example.com",
            "Patient with heart condition.",
            "Male",
            PatientStatus.CRITICAL
        ),
        Patient(
            8,
            "Laura",
            "Anderson",
            "2021-08-20",
            "1988-10-30",
            8529637410,
            "laura.anderson@example.com",
            "Patient undergoing therapy.",
            "Female",
            PatientStatus.OBSERVATION
        ),
        Patient(
            9,
            "James",
            "Taylor",
            "2021-09-12",
            "1978-04-17",
            9632587410,
            "james.taylor@example.com",
            "Patient with respiratory issues.",
            "Male",
            PatientStatus.CRITICAL
        ),
        Patient(
            10,
            "Sophia",
            "Thomas",
            "2021-10-05",
            "1986-06-14",
            3698521470,
            "sophia.thomas@example.com",
            "Patient with minor injuries.",
            "Female",
            PatientStatus.STABLE
        )
    )
}
