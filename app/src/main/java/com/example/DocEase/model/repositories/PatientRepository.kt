package com.example.DocEase.model.repositories

import com.example.DocEase.model.daos.PatientDao
import com.example.DocEase.model.models.Patients
import kotlinx.coroutines.flow.Flow

class PatientRepository(private val patientDao: PatientDao):BaseRepository<Patients> {
    override suspend fun insert(t: Patients) = patientDao.insert(t)

    override suspend fun update(t: Patients) = patientDao.update(t)

    override suspend fun delete(t: Patients) = patientDao.delete(t)

    override fun getOneStream(id: Int): Flow<Patients?> = patientDao.getPatient(id)

    fun checkPatient(email: String):Flow<Patients> = patientDao.checkPatient(email)

    override fun getAll(): Flow<List<Patients>> = patientDao.getPatients()
}