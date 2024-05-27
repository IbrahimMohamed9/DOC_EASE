package com.example.DocEase.model.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.DocEase.model.models.Schedules
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(schedule: Schedules)

    @Update
    suspend fun update(schedule: Schedules)

    @Delete
    suspend fun delete(schedule: Schedules)

    @Query("SELECT * FROM schedules WHERE scheduleId = :scheduleId")
    fun getSchedule(scheduleId: Int): Flow<Schedules>

    @Query("SELECT * FROM schedules")
    fun getSchedules(): Flow<List<Schedules>>
}

