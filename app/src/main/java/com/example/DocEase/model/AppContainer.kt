package com.example.DocEase.model

import android.content.Context
import com.example.DocEase.model.repositories.DoctorRepository
import com.example.DocEase.model.repositories.PatientRepository
import com.example.DocEase.model.repositories.ScheduleRepository

interface AppContainer {
    val doctorRepository: DoctorRepository
    val patientRepository: PatientRepository
    val scheduleRepository: ScheduleRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val doctorRepository: DoctorRepository by lazy {
        DoctorRepository(DocEaseDatabase.getDatabase(context).doctorDao())
    }

    override val patientRepository: PatientRepository by lazy {
        PatientRepository(DocEaseDatabase.getDatabase(context).patientDao())
    }

    override val scheduleRepository: ScheduleRepository by lazy {
        ScheduleRepository(DocEaseDatabase.getDatabase(context).scheduleDao())
    }
}
