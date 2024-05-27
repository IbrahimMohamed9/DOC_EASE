package com.example.DocEase.model.repositories

import com.example.DocEase.model.daos.DoctorDao
import com.example.DocEase.model.models.Doctors
import kotlinx.coroutines.flow.Flow

class DoctorRepository(private val doctorDao: DoctorDao) : BaseRepository<Doctors> {
    override suspend fun insert(t: Doctors) = doctorDao.insert(t)

    override suspend fun update(t: Doctors) = doctorDao.update(t)

    override suspend fun delete(t: Doctors) = doctorDao.delete(t)

    override fun getOneStream(id: Int): Flow<Doctors?> = doctorDao.getDoctor(id)

    override fun getAll(): Flow<List<Doctors>> = doctorDao.getDoctors()

     fun login(email: String, password: String): Flow<Doctors?> = doctorDao.login(email, password)

     fun getEmailDoctor(email: String): Flow<Doctors?> = doctorDao.getEmailDoctor(email)
}