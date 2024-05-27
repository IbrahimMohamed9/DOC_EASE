package com.example.DocEase.model.repositories

import com.example.DocEase.model.daos.ScheduleDao
import com.example.DocEase.model.models.Schedules
import kotlinx.coroutines.flow.Flow

class ScheduleRepository(private val scheduleDao: ScheduleDao):BaseRepository<Schedules> {
    override suspend fun insert(t: Schedules) = scheduleDao.insert(t)

    override suspend fun update(t: Schedules) = scheduleDao.update(t)

    override suspend fun delete(t: Schedules) = scheduleDao.delete(t)

    override fun getOneStream(id: Int): Flow<Schedules?> = scheduleDao.getSchedule(id)

    override fun getAll(): Flow<List<Schedules>> = scheduleDao.getSchedules()
}